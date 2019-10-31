# 科达快递帮

## 简介

    本项目是基于易班的，发布快递订单、接收订单的后台部分。

## 接口

### LoginController

<b>`/api/v1.0/SUSTDelivery/view/oauth`: </b>
> 从配置文件读取请求access_token的地址并返回给前端

<b>`/api/v1.0/SUSTDelivery/view/login`: </b>
> 接收从前端传来的access_token，调用<i>已封装好的接口[^1]</i>获取易班个人信息，并使用易班id查找数据库是否存在该用户，如果存在，则将完整的用户信息封装成用户对象返回给前端；如果不存在，则将不完整的信息存入数据库，并将不完整的信息封装成用户对象返回给前端。

<b>`/api/v1.0/SUSTDelivery/view/addInfo`: </b>
> 当前端判断用户的信息不完整时，将会引导至一个信息补全的页面，当用户补全信息后请求此接口，将用户补全的信息存入数据库，并将完整的用户信息封装成用户对象返回给前端。


[^1]: https://github.com/Nightnessss/yiban-accessToken