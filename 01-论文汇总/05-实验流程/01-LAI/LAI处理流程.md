```python
#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
LAI数据插补和合并脚本
处理LAI时间序列数据并与通量数据合并
"""

import pandas as pd
import numpy as np
from datetime import datetime, timedelta
from sklearn.ensemble import RandomForestRegressor
from sklearn.preprocessing import StandardScaler
import warnings
warnings.filterwarnings('ignore')

def load_and_prepare_lai_data(lai_file):
    """加载和预处理LAI数据"""
    print("正在加载LAI数据...")
    lai_df = pd.read_csv(lai_file)
    
    # 转换时间格式
    lai_df['record_time'] = pd.to_datetime(lai_df['record_time'])
    
    # 添加时间特征用于插补
    lai_df['year'] = lai_df['record_time'].dt.year
    lai_df['month'] = lai_df['record_time'].dt.month
    lai_df['day_of_year'] = lai_df['record_time'].dt.dayofyear
    lai_df['season'] = lai_df['month'].map({
        12: 'winter', 1: 'winter', 2: 'winter',
        3: 'spring', 4: 'spring', 5: 'spring',
        6: 'summer', 7: 'summer', 8: 'summer',
        9: 'autumn', 10: 'autumn', 11: 'autumn'
    })
    
    print(f"原始LAI数据形状: {lai_df.shape}")
    print(f"站点数量: {lai_df['site_id'].nunique()}")
    print(f"时间范围: {lai_df['record_time'].min()} 到 {lai_df['record_time'].max()}")
    
    return lai_df

def interpolate_lai_for_site(site_data):
    """为单个站点插补LAI数据"""
    site_id = site_data['site_id'].iloc[0]
    print(f"正在为站点 {site_id} 插补LAI数据...")
    
    # 创建完整的时间序列
    start_date = site_data['record_time'].min()
    end_date = site_data['record_time'].max()
    
    # 创建每日时间序列
    date_range = pd.date_range(start=start_date, end=end_date, freq='D')
    complete_df = pd.DataFrame({
        'record_time': date_range,
        'site_id': site_id
    })
    
    # 合并现有数据
    complete_df = complete_df.merge(site_data, on=['record_time', 'site_id'], how='left')
    
    # 添加时间特征
    complete_df['year'] = complete_df['record_time'].dt.year
    complete_df['month'] = complete_df['record_time'].dt.month
    complete_df['day_of_year'] = complete_df['record_time'].dt.dayofyear
    complete_df['season'] = complete_df['month'].map({
        12: 'winter', 1: 'winter', 2: 'winter',
        3: 'spring', 4: 'spring', 5: 'spring',
        6: 'summer', 7: 'summer', 8: 'summer',
        9: 'autumn', 10: 'autumn', 11: 'autumn'
    })
    
    # 季节性插补
    season_medians = site_data.groupby('season')['LAI'].median()
    for season in season_medians.index:
        mask = (complete_df['season'] == season) & complete_df['LAI'].isna()
        complete_df.loc[mask, 'LAI'] = season_medians[season]
    
    # 线性插值
    complete_df['LAI'] = complete_df['LAI'].interpolate(method='linear')
    
    # 向前填充和向后填充处理边界值
    complete_df['LAI'] = complete_df['LAI'].fillna(method='ffill').fillna(method='bfill')
    
    # 如果还有缺失值，用全站点均值填充
    if complete_df['LAI'].isna().any():
        overall_mean = site_data['LAI'].mean()
        complete_df['LAI'] = complete_df['LAI'].fillna(overall_mean)
    
    return complete_df[['site_id', 'record_time', 'LAI']]

def interpolate_lai_data(lai_df):
    """插补所有站点的LAI数据"""
    print("开始LAI数据插补...")
    
    interpolated_dfs = []
    for site_id in lai_df['site_id'].unique():
        site_data = lai_df[lai_df['site_id'] == site_id].copy()
        interpolated_site = interpolate_lai_for_site(site_data)
        interpolated_dfs.append(interpolated_site)
    
    # 合并所有站点的插补数据
    interpolated_lai = pd.concat(interpolated_dfs, ignore_index=True)
    
    print(f"插补后LAI数据形状: {interpolated_lai.shape}")
    return interpolated_lai

def merge_with_flux_data(interpolated_lai, flux_file, output_file):
    """将插补后的LAI数据与通量数据合并"""
    print("正在加载通量数据...")
    flux_df = pd.read_csv(flux_file)
    flux_df['record_time'] = pd.to_datetime(flux_df['record_time'])
    
    print(f"通量数据形状: {flux_df.shape}")
    print(f"通量数据站点: {sorted(flux_df['site_id'].unique())}")
    
    # 合并数据
    print("正在合并LAI数据和通量数据...")
    merged_df = flux_df.merge(
        interpolated_lai[['site_id', 'record_time', 'LAI']], 
        on=['site_id', 'record_time'], 
        how='left'
    )
    
    print(f"合并后数据形状: {merged_df.shape}")
    print(f"LAI缺失值数量: {merged_df['LAI'].isna().sum()}")
    
    # 对于没有LAI数据的站点，使用同类型站点的均值
    if merged_df['LAI'].isna().any():
        print("为缺失LAI的站点填充值...")
        
        # 如果有land_use_type列，按土地利用类型填充
        if 'land_use_type' in merged_df.columns:
            for land_use in merged_df['land_use_type'].unique():
                if pd.isna(land_use):
                    continue
                mask = (merged_df['land_use_type'] == land_use) & merged_df['LAI'].isna()
                if mask.any():
                    # 找到有LAI数据的同类型站点的均值
                    same_type_lai = merged_df[
                        (merged_df['land_use_type'] == land_use) & 
                        merged_df['LAI'].notna()
                    ]['LAI'].mean()
                    
                    if not pd.isna(same_type_lai):
                        merged_df.loc[mask, 'LAI'] = same_type_lai
        
        # 如果还有缺失值，用全部站点的均值填充
        if merged_df['LAI'].isna().any():
            overall_mean = interpolated_lai['LAI'].mean()
            merged_df['LAI'] = merged_df['LAI'].fillna(overall_mean)
    
    # 保存合并后的数据
    merged_df.to_csv(output_file, index=False)
    print(f"合并后的数据已保存到: {output_file}")
    
    # 统计信息
    print("\n=== LAI特征统计 ===")
    print(f"记录数: {len(merged_df):,}")
    print(f"LAI均值: {merged_df['LAI'].mean():.3f}")
    print(f"LAI标准差: {merged_df['LAI'].std():.3f}")
    print(f"LAI范围: [{merged_df['LAI'].min():.3f}, {merged_df['LAI'].max():.3f}]")
    print(f"LAI缺失值: {merged_df['LAI'].isna().sum()}")
    
    # 按站点统计
    print("\n=== 各站点LAI统计 ===")
    lai_stats = merged_df.groupby('site_id')['LAI'].agg(['count', 'mean', 'std', 'min', 'max'])
    print(lai_stats.round(3))
    
    return merged_df

def update_summary_file(summary_file, merged_df):
    """更新FINAL_DATA_SUMMARY.txt文件"""
    print("正在更新数据汇总文件...")
    
    # 计算LAI与GPP的相关性
    if 'gpp' in merged_df.columns:
        lai_gpp_corr = merged_df['LAI'].corr(merged_df['gpp'])
    else:
        lai_gpp_corr = None
    
    new_content = f"""

### 17. 叶面积指数LAI特征 (文件17) - 最新数据集
**生成文件**: 17_add_LAI.csv
**基于文件**: 16_add_ta_swc.csv
**新增特征**: LAI (叶面积指数)
**生态学意义**: 植被叶面积密度，反映植被光合作用能力
**数据来源**: 北京站点LAI时间序列数据 (Google Earth Engine)
**插补方法**: 多层次插补策略
  - 季节性中位数插补
  - 线性插值
  - 前向后向填充
  - 站点类型均值填充
**统计信息**:
  - 记录数: {len(merged_df):,}
  - 特征数: {len(merged_df.columns)} (38 + 1新增)
  - 缺失值: {merged_df['LAI'].isna().sum()} ({merged_df['LAI'].isna().sum()/len(merged_df)*100:.1f}%覆盖率)
  - 均值: {merged_df['LAI'].mean():.3f}
  - 标准差: {merged_df['LAI'].std():.3f}
  - 范围: [{merged_df['LAI'].min():.3f}, {merged_df['LAI'].max():.3f}]"""
    
    if lai_gpp_corr is not None:
        new_content += f"""
**与GPP相关性**: {lai_gpp_corr:.3f}"""
    
    new_content += f"""
**协同效应**: LAI作为重要的植被结构参数，与光谱指数形成互补

## 🎯 最终数据集特征清单更新 (17_add_LAI.csv)

### 新增植被结构特征 (1个特征)
39. LAI - 叶面积指数 (植被叶面积密度)

**完整特征分类 (39个特征)：**
- 📍 基础信息 (2个): site_id, record_time
- 🌡️ 通量特征 (10个): 气象、辐射、GPP、APAR等
- 🗺️ 地理特征 (6个): 经纬度、海拔、土地利用
- 🛰️ 光谱特征 (6个): Blue, Green, Red, NIR, SWIR1, SWIR2
- 📊 植被指数 (3个): NDVI, EVI, NIRv
- ⏰ 时间特征 (1个): DOY
- 🌿 生长季特征 (3个): 开始/峰值/结束日
- ⏳ 滞后特征 (5个): APAR(t-1), VPD(t-1), swc(t-1/3/7)
- 🤝 交互特征 (2个): APAR_NDVI_interaction, ta_swc_interaction
- 🌱 植被结构 (1个): LAI

**LAI特征优势**:
- 🎯 直接反映植被光合面积
- 🌿 补充光谱指数信息
- 📊 增强生态过程理解
- 🔬 提供植被结构细节

这使得数据集在植被生态建模方面更加完整和精确。

"""
    
    # 读取现有文件并添加新内容
    try:
        with open(summary_file, 'r', encoding='utf-8') as f:
            existing_content = f.read()
        
        # 在文件末尾添加新内容
        updated_content = existing_content + new_content
        
        with open(summary_file, 'w', encoding='utf-8') as f:
            f.write(updated_content)
        
        print(f"数据汇总文件已更新: {summary_file}")
    except Exception as e:
        print(f"更新汇总文件时出错: {e}")

def main():
    """主函数"""
    print("=== LAI数据插补和合并处理 ===\n")
    
    # 文件路径
    lai_file = "gee_data/Beijing_Sites_LAI_TimeSeries_Data.csv"
    flux_file = "processed_qc_flux_data/16_add_ta_swc.csv"
    output_file = "processed_qc_flux_data/17_add_LAI.csv"
    summary_file = "processed_qc_flux_data/FINAL_DATA_SUMMARY.txt"
    
    try:
        # 1. 加载和预处理LAI数据
        lai_df = load_and_prepare_lai_data(lai_file)
        
        # 2. 插补LAI数据
        interpolated_lai = interpolate_lai_data(lai_df)
        
        # 3. 与通量数据合并
        merged_df = merge_with_flux_data(interpolated_lai, flux_file, output_file)
        
        # 4. 更新汇总文件
        update_summary_file(summary_file, merged_df)
        
        print("\n=== 处理完成 ===")
        print(f"✅ 最终数据集: {output_file}")
        print(f"✅ 特征数量: {len(merged_df.columns)}")
        print(f"✅ 记录数量: {len(merged_df):,}")
        print(f"✅ LAI覆盖率: {(1-merged_df['LAI'].isna().sum()/len(merged_df))*100:.1f}%")
        
    except Exception as e:
        print(f"❌ 处理失败: {e}")
        import traceback
        traceback.print_exc()

if __name__ == "__main__":
    main()
```