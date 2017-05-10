
### 工程说明：
1. wtb-biz：业务服务相关接口
1. wtb-biz-impl：业务服务相关实现，这两个工程跟基本跟顽童堡无关，顽童堡只用到基础的创建用户的Service
1. wtb-service-wei：移动终端的业务服务相关接口，只需要关注WanTongBaoService
1. wtb-service-wei-impl：移动终端的业务服务相关实现，里面包括数据库的访问层，ORM使用mybatis
1. wtb-web-core：主要是控制层的代码，你可以理解成Action或者SpringMVC的Controller
1. wtb-web：这个工程是webapp，主要包括前台页面jsp，配置文件，静态资源文件等

### 如何找到页面对应的业务代码：
你可以找到顽童堡页面定义文件：WEB-INF/config/kplat-web/op_wtb.xml
里面的内容基本如下：
```xml
    <operation uri="/wtb/location" name="找到我们">
		<step id="toLocation"
			  class="cn.kanejin.kplat.web.opstep.ForwardPage">
			<return-action>
				<if return="0"><forward page="/WEB-INF/view/wtb/location.jsp" /></if>
				<else><error/></else>
			</return-action>
		</step>
	</operation>
```
说明：

* ```operation``` 一个operation对应一个页面的url
* ```uri``` 页面访问的uri，比如```/wtb/location```，页面访问url就是```http://yourdomain.com/wtb/location```
* ```name``` 页面的名称
* ```step``` 一个operation需要执行的步骤，有可以这个步骤什么就不做就只是单纯跳转到jsp
* ```step/class``` 这个就是前台页面访问时要执行的控制层的类文件
* ```return-action``` 执行完step的步骤之后返回的jsp页面


### 数据库用到的Table
* ```T_MICRO_ORDER``` 订单，用户下单购买会员卡的记录
* ```T_MICRO_PRODUCT``` 保存有哪几种会员卡
* ```T_MICRO_PRODUCT_SKU``` 会员卡的型号，比如记名卡有半年卡、季卡之分
* ```T_USER``` 用户信息
* ```T_USER_WEIXIN``` 用户的微信信息
* ```T_WTB_SURVEY``` 调查问卷记录

