# 0 AI提示语
我刚学java 使用我能听得懂的语言解释 使用图片或者类比等方法
# 1 Java基础

流
```
List<Integer> list = new ArrayList<>();  
list.add(3);  
list.add(1);  
list.add(2);  
// Collections.sort(list);  
int sum = list.stream()  //把东西放在传送带上  
        .mapToInt(Integer::intValue)  //拆掉包装  
        .sum();  //用电子称逐个计算重量
```

# 2 Spring Boot路线
### 第一阶段：理解基础架构

1. **学习Spring Boot核心概念**
    - 理解依赖注入和控制反转(IoC)
    - 学习Spring Boot自动配置原理
    - 熟悉项目结构和配置文件(application.yml)
2. **掌握基本注解**
    - 理解`@Controller`, `@Service`, `@Repository`等注解作用
    - 学习请求映射和参数绑定方式

### 第二阶段：理解数据访问

1. **学习MyBatis基础**
    - 理解`@Mapper`接口和XML映射文件的关系
    - 学习基本SQL操作和参数传递方式
2. **了解Redis集成**
    - 理解Redis在项目中的用途(Token存储)
    - 学习`StringRedisTemplate`的基本使用

### 第三阶段：深入业务逻辑

1. **分析认证流程**
    - 理解JWT的生成和验证流程
    - 学习密码加密和校验方法
2. **学习异常处理**
    - 理解自定义异常类的设计
    - 学习全局异常处理方式

### 第四阶段：实践扩展

1. **添加新功能**
    - 尝试添加用户信息更新功能
    - 实现用户权限控制
2. **优化现有代码**
    - 添加更完善的验证逻辑
    - 增强安全性措施


# 3 代码使用方法
## 3.1. 日志------在需要记录的控制器方法上加上注解

```
@LogOperation(value = "{记录名}", module = "{模块名}")
public String addUser(User user) {
    ...
}
```





# 4 Leetcode

- **字符串处理**（反转、子串、括号匹配）：
    
    - 经典题：LeetCode 344（反转）、5（最长回文）、20（有效括号）
        
- **双指针/滑动窗口**：
    
    - 经典题：141（环形链表）、3（无重复最长子串）、76（最小覆盖子串）
        
- **二叉树与递归**：
    
    - 经典题：104（最大深度）、226（翻转）、102（层次遍历）
        
- **动态规划（基础）**：
    
    - 经典题：70（爬楼梯）、53（最大子数组）、121（买卖股票）
        
- **二分查找**：
    
    - 经典题：704（标准二分）、33（旋转数组搜索）
        
- **栈/队列**：
    
    - 经典题：155（最小栈）、232（队列实现）
## 0409
#### 前中后序遍历
##### 知识点地址
https://programmercarl.com/%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E8%BF%AD%E4%BB%A3%E9%81%8D%E5%8E%86.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
##### leetcode
- https://leetcode.cn/problems/binary-tree-preorder-traversal/
- https://leetcode.cn/problems/binary-tree-inorder-traversal/
- https://leetcode.cn/problems/binary-tree-postorder-traversal/


# 5，接口通用步骤及模板
## 添加新接口的标准流程

### 1. 数据模型设计

- 确定新接口需要的数据模型
- 在`org.example.entity`包中创建相应的实体类
- 使用Lombok的`@Data`注解简化getter/setter

### 2. 创建DTO对象

- 在`org.example.dto.request`包中创建请求DTO
- 在`org.example.dto.response`包中创建响应DTO
- 添加必要的数据验证注解(如`@NotBlank`)

### 3. 编写Mapper接口

- 在`org.example.mapper`包中创建或修改Mapper接口
- 定义所需的数据库操作方法
- 添加`@Mapper`注解确保MyBatis识别

### 4. 编写XML映射文件

- 在`src/main/resources/mapper`目录下创建或修改XML文件
- 编写SQL语句实现Mapper中定义的方法
- 确保namespace与Mapper接口路径一致

### 5. 定义Service接口

- 在`org.example.service`包中创建或修改Service接口
- 定义业务逻辑方法

### 6. 实现Service接口

- 在`org.example.service.impl`包中创建或修改实现类
- 使用`@Service`注解标记为服务类
- 实现业务逻辑，包括异常处理和数据验证
- 需要事务支持的方法添加`@Transactional`注解

### 7. 编写Controller

- 在`org.example.controller`包中创建或修改Controller类
- 使用`@RestController`和`@RequestMapping`注解
- 添加`@LogOperation`注解实现操作日志记录
- 定义RESTful端点，使用适当的HTTP方法
- 统一使用`Result`类包装响应结果

### 8. 添加权限控制

- 确定接口的访问权限要求
- 在Security配置中添加相应的权限控制规则
- 需要的话，使用`@PreAuthorize`等注解实现方法级权限控制

### 9. 测试接口

- 编写单元测试验证业务逻辑
- 使用工具(如Postman)进行接口测试
- 验证权限控制、输入验证等功能

### 10. 完善文档和日志

- 添加接口文档注释
- 确保日志记录完整
- 更新API文档(如有)

## 示例代码模板

### 实体类模板

java

```java
package org.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NewEntity {
    private Integer id;
    private String name;
    private LocalDateTime createdTime;
    // 其他字段...
}
```

### Mapper接口模板

java

```java
package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.NewEntity;
import java.util.List;

@Mapper
public interface NewEntityMapper {
    int insert(NewEntity entity);
    NewEntity findById(@Param("id") Integer id);
    List<NewEntity> findAll();
    int update(NewEntity entity);
    int deleteById(@Param("id") Integer id);
}
```

### Service接口模板

java

```java
package org.example.service;

import org.example.dto.request.NewEntityRequest;
import org.example.dto.response.NewEntityResponse;
import java.util.List;

public interface NewEntityService {
    NewEntityResponse create(NewEntityRequest request);
    NewEntityResponse getById(Integer id);
    List<NewEntityResponse> getAll();
    NewEntityResponse update(Integer id, NewEntityRequest request);
    void delete(Integer id);
}
```

### Controller模板

java

```java
package org.example.controller;

import org.example.annotation.LogOperation;
import org.example.common.Result;
import org.example.dto.request.NewEntityRequest;
import org.example.dto.response.NewEntityResponse;
import org.example.service.NewEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/new-entity")
@Validated
public class NewEntityController {

    @Autowired
    private NewEntityService newEntityService;

    @PostMapping
    @LogOperation(value = "创建新实体", module = "新实体管理")
    public Result<NewEntityResponse> create(@RequestBody @Validated NewEntityRequest request) {
        return Result.success(newEntityService.create(request));
    }

    @GetMapping("/{id}")
    @LogOperation(value = "获取新实体", module = "新实体管理")
    public Result<NewEntityResponse> getById(@PathVariable Integer id) {
        return Result.success(newEntityService.getById(id));
    }

    @GetMapping
    @LogOperation(value = "获取所有新实体", module = "新实体管理")
    public Result<List<NewEntityResponse>> getAll() {
        return Result.success(newEntityService.getAll());
    }

    @PutMapping("/{id}")
    @LogOperation(value = "更新新实体", module = "新实体管理")
    public Result<NewEntityResponse> update(@PathVariable Integer id, 
                                          @RequestBody @Validated NewEntityRequest request) {
        return Result.success(newEntityService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @LogOperation(value = "删除新实体", module = "新实体管理")
    public Result<Void> delete(@PathVariable Integer id) {
        newEntityService.delete(id);
        return Result.success();
    }
}
```

遵循这个流程和模板，你可以轻松地向项目中添加新的接口，并确保它们与现有代码保持一致的风格和架构。