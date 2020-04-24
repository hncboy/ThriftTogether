# ThriftTogether

开发者：[hncboy](https://github.com/hncboy)、[KevinMcblack](https://github.com/KevinMcblack)、[vergec](https://github.com/vergec)

完成于 2019年5月，基于 Android 平台的团购 APP Demo，仅仅是个课程大作业，时间原因，部分功能待实现，图片仅为样例，有些功能可能不存在或者是假数据。[后台代码](https://github.com/hncboy/ThriftTogetherBackstage) 

# 1.软件概述
## 1.1 概述
团节是一个团购平台，团购（Group purchase）就是团体购物，指认识或不认识的消费者联合起来，加大与商家的谈判能力，以求得最优价格的一种购物方式。团节通过与商家的合作，根据薄利多销的原理，商家可以给出低于零售价格的团购折扣和单独购买得不到的优质服务。团节实现团购商品的查看、选购、支付的一站式团购平台，以低廉的价格让用户可以在家完成购物过程。
## 1.2 功能
- （1）用户登录：用户进入登录界面，输入用户名（手机号）、密码验证身份，进入首页。
- （2）用户注册：用户阅读并同意相关协议后，通过手机号码进行注册并设置初始密码。
- （3）忘记密码：用户忘记登录密码时，可凭短信验证找回密码。
- （4）密码修改：用户可进入个人中心进行登录密码的修改。
- （5）意见反馈：用户可对软件各个方面进行意见反馈并提交
- （6）主菜单：显示首页、发现、商城、我的等子菜单图标，首页提供各个类别商家入口等。
- （7）商品分类：按照美食、电影、酒店、KTV、丽人、休闲娱乐等将商品和店铺进行分类展示。
- （8）今日推荐：推荐店铺集中展示，每天更新。
- （9）猜你喜欢：根据用户历史搜索数据进行统计和预测，筛选用户经常浏览或消费的商品和商家。
- （10）预定：用户选择商品进行预定，商家收到预定消息并进行预定管理。
- （11）支付：用户对消费的商品进行安全支付。
- （12）二维码扫描：商家通过扫描用户订单的二维码实现订单的使用。
- （13）积分商城：用户可以用评价订单获得积分兑换商城中的优惠券。
- （14）发现：通过定位功能查询附件的商家并可以进行地图展示和列表展示。

## 1.3 性能
软件具有良好的易用性和可靠性，并保证信息的安全性和保密性。

# 2. 运行环境
安卓开发环境可在Android 3.4.1上运行。

后台开发环境可在IntelliJ IDEA 2019.1.2 (Ultimate Edition)上运行。

APP端可在Android7版本以上的手机运行

# 3. 使用方法
## 3.1 软件安装
使用 AndroidStudio 运行项目。
## 3.2 软件运行
用户在安装完毕后，点击团节图标即可进入系统，界面如图：
<div align = "center">  
    <img width="200px" height="400px" src="pics/dac11f47-853f-437a-8ab8-0debae7d0f5a.png" />
</div>
<div align = "center"> 图 3-1 安装运行 </div><br>

# 4.界面

## 4.1 用户注册

​	注册界面（功能暂未实现）

<div align = "center">  
    <img width="200px" height="400px" src="pics/f799e059-f8f0-4523-9eaf-231315ddbe04.png" />
</div>
<div align = "center"> 图 4-1 注册界面 </div><br>

## 4.2 用户登录

​	允许定位权限，登录界面（功能暂未实现），用户id目前是固定的。

<div align = "center">  
    <img width="200px" height="400px" src="pics/38658865-f90b-4103-b49b-e6f2676b948b.png" />
    <img width="200px" height="400px" src="pics/4af3906f-9d90-4aff-a63a-10f4d6271397.png" />
</div>
<div align = "center"> 图 4-2 登录界面 </div><br>

## 4.3 首页

<div align = "center">  
    <img width="200px" height="400px" src="pics/781281a2-01c5-471a-b393-33c9dbf85ee5.png" />
</div>
<div align = "center"> 图 4-3 首页界面 </div><br>

### 4.3.1 切换城市

​	登陆用户通过点击首页左上角的定位或城市名进入切换城市界面，界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/2d935ce1-6513-4374-9b30-b34fb64ddb7a.png" />
</div>
<div align = "center"> 图 4-4 切换城市界面 </div><br>

### 4.3.2 搜索店铺

​	登录用户通过首页顶部的搜索框进入搜索页面，直接输入搜索内容或者点击热门搜索或历史搜索的记录也可以直接搜索，点击搜索结果中的店铺即可进入店铺详情页，这里的搜索用到了 ElasticSearch，支持中文分词，界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/3f9fbe5a-16d2-43ec-9e1e-3002805879a6.png" />
    <img width="200px" height="400px" src="pics/36037485-a336-40b5-9dd7-ce41c4e2b498.png" />
</div>
<div align = "center"> 图 4-5 搜索店铺界面 </div><br>

### 4.3.3 扫描二维码

​	登录用户点击首页右上角扫描图标即可进入扫描二维码界面，界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/531f780a-5c6f-4b97-b3db-3fdcac781521.png" />
</div>
<div align = "center"> 图 4-6 扫描二维码界面 </div><br>

### 4.3.4 今日推荐

​	用户会在首页中看到今日推荐店铺，推荐店铺集中展示，每天定时更新，界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/8c181ac8-c4ad-4dbb-b090-5294710fe45c.png" />
</div>
<div align = "center"> 图 4-7 今日推荐界面 </div><br>

### 4.3.5 猜你喜欢

​	用户在首页往下滑动时，会看到猜你喜欢的店铺，猜你喜欢根据用户历史搜索数据进行统计和预测，筛选用户经常浏览或消费的商品和商家。点击猜你喜欢标题旁边的箭头可进入猜你喜欢详情页，页面以卡片的形式展示店铺，可左右滑动筛选店铺，点击则可以进入店铺详情页，界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/bc9a3f5b-1e45-440b-bf2e-05666d543373.png" />
    <img width="200px" height="400px" src="pics/96cd3ae2-95ae-4119-9464-90cf3c7a4a70.png" />
    <img width="200px" height="400px" src="pics/1f4ebac5-75b0-4481-bfdc-6266248ea740.png" />
</div>
<div align = "center"> 图 4-8 搜索店铺界面 </div><br>

### 4.3.6 美食

​	用户可以点击首页中的美食图标进入美食页面，在美食页面点击右上角的搜索可进入搜索页面，点击排序和筛选即可查询自己所需要的店铺（功能暂未实现），点击店铺可进入店铺详情页，界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/4bfaa30e-1fef-40d2-849f-0e1093c022fb.png" />
    <img width="200px" height="400px" src="pics/663ffa7e-483f-4d34-a55d-cb0414fa602d.png" />
    <img width="200px" height="400px" src="pics/e48f128e-3e95-4584-aee9-e20ade5e1335.png" />
</div>
<div align = "center"> 图 4-9 美食界面 </div><br>

### 4.3.7 电影

​	用户可以点击首页中的电影图标进入电影页面，点击具体的电影可进入选择影院界面，点击影院可进入选择时间购买界面（WebView 套用了美团的页面），界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/a7fa7fc1-e108-4ee9-9410-11854cd2df8f.png" />
    <img width="200px" height="400px" src="pics/aee7d488-7622-463a-a53a-2c86dfa4635b.png" />
    <img width="200px" height="400px" src="pics/8dc7730e-3ec0-4243-995b-2ba9b4f01c17.png" />
</div>
<div align = "center"> 图 4-10 电影界面 </div><br>

### 4.3.8 酒店

​	用户可以点击首页中的酒店图标进入酒店页面，可切换国内、国际/港澳台、民宿公寓，输入目的地、时间、关键词等可搜索酒店。点击时间可筛选入住和离店时间（功能暂未实现）。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/9c8730e2-b251-46f0-ab7a-ad663ef72498.png" />
    <img width="200px" height="400px" src="pics/0bd14586-03b8-4bd3-9925-8ef6a94fc986.png" />
</div>
<div align = "center"> 图 4-11 酒店界面 </div><br>

### 4.3.9 丽人

​	用户可以点击首页中的丽人图标进入丽人页面，在丽人页面点击右上角的搜索可进入搜索页面，点击排序和筛选即可查询自己所需要的店铺，点击店铺可进入店铺详情页，（功能暂未实现）界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/f3bf3355-d333-439c-948f-a4f64fe42244.png" />
    <img width="200px" height="400px" src="pics/5637080e-42b8-4ef5-a91d-6381c8d5a59c.png" />
    <img width="200px" height="400px" src="pics/0ce7f9ac-dd8a-43c9-b063-d103f0ac0f98.png" />
</div>
<div align = "center"> 图 4-12 丽人界面 </div><br>

### 4.3.10 KTV

​	用户可以点击首页中的KTV图标进入KTV页面，在KTV页面点击右上角的搜索可进入搜索页面，点击排序和筛选即可查询自己所需要的店铺，点击店铺可进入店铺详情页，（功能暂未实现）界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/8357790c-e496-4dc5-b912-88a354c46132.png" />
    <img width="200px" height="400px" src="pics/d6eeee47-3d98-45af-bf64-fcf4b3871487.png" />
    <img width="200px" height="400px" src="pics/a6464ee6-dbf4-4566-8117-3033a53caf1a.png" />
</div>
<div align = "center"> 图 4-13 KTV界面 </div><br>

### 4.3.11 休闲娱乐

​	用户可以点击首页中的休闲娱乐图标进入休闲娱乐页面，在休闲娱乐页面点击右上角的搜索可进入搜索页面，点击排序和筛选即可查询自己所需要的店铺，点击店铺可进入店铺详情页，（功能暂未实现）界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/58f3405d-b968-4f78-9ee2-8298f3a7c3fa.png" />
    <img width="200px" height="400px" src="pics/4ccadfc8-df4d-4726-98cb-605a3b4bcd5e.png" />
    <img width="200px" height="400px" src="pics/77e154cf-5fe7-48bc-9864-0404b5e9cfc4.png" />
</div>
<div align = "center"> 图 4-14 休闲娱乐界面 </div><br>

## 4.4 发现

### 4.4.1 周边

​	用户点击发现子菜单可进入发现页面，发现页面上半部分为地图，下半部分为店铺信息，根据用户定位，从近到远显示店铺信息，点击店铺对应地图上也会跳到店铺所在位置并显示店铺信息，点击地图上的店铺信息可进入店铺详情页。界面如图：

### 4.4.2 导航

​	用户在发现页点击店铺条目右侧的导航图标，进入选择第三方导航软件界面，任意选择一个即可进入APP导航，如若未安装则无法进入。界面如图：

## 4.5 商城

​	用户在子菜单中选择商城，进入商城页，在商城页用户可用评价获得积分兑换优惠券，优惠券在用户购买团购产品可优惠。界面如下：

<div align = "center">  
    <img width="200px" height="400px" src="pics/4be9dc76-69b9-45db-b3bc-b5a99121c793.png" />
    <img width="200px" height="400px" src="pics/5cf519b9-cd70-45b9-a75f-0b25be198ec8.png" />
    <img width="200px" height="400px" src="pics/eaf47ed1-b097-4775-b85c-1d7ab0a5b1b7.png" />
</div>
<div align = "center"> 图 4-15 商城界面 </div><br>

## 4.6 店铺

### 4.6.1 店铺详情

​	点击任意店铺条目（从搜索中，首页中，分类中，收藏中，最近浏览中等）会进入店铺详情页，店铺详情界面包含了店铺图片、位置、团购和评价等信息。界面右上方收藏按钮可以收藏店铺，收藏的店铺会进入我的收藏店铺中。点击预约会进入预约界面。点击抢购会进入团购界面。往下滑动则是店铺团购评论。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/fe29f6dd-46d9-41ed-8120-63188c25aabc.png" />
</div>
<div align = "center"> 图 4-16 店铺详情界面 </div><br>

### 4.6.2 店铺评论

​	店铺详情页往下滑动会显示用户评论，包含了用户评价时间、购买的团购商品、评分、评价内容和评价图片等信息。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/07f2b93a-7ae2-494a-8716-98e7d09295dd.png" />
</div>
<div align = "center"> 图 4-17 店铺评论界面 </div><br>

### 4.6.3 预约店铺

​	在店铺详情页点击预约则可预约店铺，首先选择预约时间，然后输入预约详细信息则可以进行预约，预约信息可以在我的预约中查看。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/bde6bb2d-a4f4-4aea-a8e7-778cff817448.png" />
    <img width="200px" height="400px" src="pics/6327ad8c-c0bb-4ec5-9449-4648d44f614a.png" />
</div>
<div align = "center"> 图 4-18 预约店铺界面 </div><br>

### 4.6.4 团购详情

​	在店铺详情页点击团购商品的抢购即可进入团购详情页，左右滑动可切换团购套餐，点击选择团购标题右侧的收藏按钮即可收藏当前的团购商品，可在我的收藏商品中查看。团购详情信息包含了，商品销量、套餐详情、温馨提醒等信息，价格显示为团节团购价，点击立即购买可进入确认订单页。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/7dacd379-4c90-4dee-b4c0-0dc98cf91164.png" />
    <img width="200px" height="400px" src="pics/eecae12b-d70d-4ced-816e-0aaf0bc4ddbf.png" />
</div>
<div align = "center"> 图 4-19 团购详情界面 </div><br>

### 4.6.5 确认订单

​	在团购详情页选择团购商品点击购买可进入确认订单页，在确认订单页可修改商品数量，选择用户的优惠券进行使用，点击提交提交订单即可支付，使用支付宝进行支付，支付成功则跳转到使用订单界面，支付失败跳转到待付款页面。未使用优惠券，选择优惠券，使用优惠券界面分别如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/4e5f4977-c7dc-46a0-a739-da3ecb9b3ca1.png" />
    <img width="200px" height="400px" src="pics/8164f23d-d5ee-4811-a3f3-9889effc5cfe.png" />
    <img width="200px" height="400px" src="pics/acb19e2b-3c30-48a1-85cf-d0f1b250803a.png" />
</div>
<div align = "center"> 图 4-20 使用优惠券，选择优惠券，使用优惠券界面 </div><br>
​	支付界面、支付成功界面、支付失败界面分别如图所示（支付采用支付宝沙盒）：

<div align = "center">  
    <img width="200px" height="400px" src="pics/f82356f7-e5fd-4aec-ae58-847949c102c7.png" />
    <img width="200px" height="400px" src="pics/7fd30cd4-9212-46f8-95c2-52f588eacdc8.png" />
    <img width="200px" height="400px" src="pics/737e6b34-cc5e-462c-ba30-d6d3d1ac7dd9.png" />
</div>
<div align = "center"> 图 4-21 支付界面、支付成功界面、支付失败界面 </div><br>

## 4.7 我的

### 4.7.1 我的详情

​	我的页面包含了上方：右上角的通知图标、用户头像、用户名、收藏、最近浏览、券包，中间订单部分和下方我的预定、通用设置、意见反馈、版本更新和咨询客服等功能。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/d6c63c44-3382-4cd1-a24a-a3d06bea592b.png" />
</div>
<div align = "center"> 图 4-22 我的详情界面 </div><br>

### 4.7.2 我的收藏

​	在我的页面中点击收藏进入我的收藏页面，我的收藏页面包含了收藏的商家和商品，左右滑动可切换，长按条目可删除。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/5c86a6ed-6c0f-4943-ba99-2538fc13ade2.png" />
    <img width="200px" height="400px" src="pics/df162f4b-d76e-4956-933e-331e7e8e0af9.png" />
    <img width="200px" height="400px" src="pics/943e7548-bd27-4f16-99c4-c1fae89fe68c.png" />
</div>
<div align = "center"> 图 4-23 我的收藏界面 </div><br>

### 4.7.3 最近浏览

​	在我的页面中点击最近浏览可进入最近浏览页面，该界面包含了用户最近查看过的店铺。界面如下：

<div align = "center">  
    <img width="200px" height="400px" src="pics/9be3a67e-acd1-480c-bc48-ceb7272e3435.png" />
</div>
<div align = "center"> 图 4-24 最近浏览界面 </div><br>

### 4.7.4 我的券包

​	在我的页面中点击券包可进入券包页面，该界面包含了用户领取的优惠券，按未使用、已使用、已过期显示。界面如下：

<div align = "center">  
    <img width="200px" height="400px" src="pics/00208342-0138-4ade-9c54-11b1fbb3ac2c.png" />
</div>
<div align = "center"> 图 4-25 我的券包界面 </div><br>

### 4.7.5 活动通知

​	在我的页面中点击右上角铃铛图标可进入活动通知页面，该界面包含了APP向用户推送的店铺优惠活动信息。界面如下：

<div align = "center">  
    <img width="200px" height="400px" src="pics/cb36b8f1-9181-46b4-9991-e95e8a81e214.png" />
    <img width="200px" height="400px" src="pics/07ed389d-9d15-47c0-a66c-ea86815403d5.png" />
</div>
<div align = "center"> 图 4-26 活动通知界面 </div><br>

### 4.7.6 意见反馈

​	在我的页面中点击意见反馈可进入意见反馈页面，用户可对软件各个方面进行意见反馈并提交，界面如下：

<div align = "center">  
    <img width="200px" height="400px" src="pics/3fd98795-1357-425a-b0ea-3d445d0aadfa.png" />
</div>
<div align = "center"> 图 4-27 意见反馈界面 </div><br>

### 4.7.7 通用设置

​	在我的页面中点击通用设置可进入设置页面，该页面可以修改一些设置如支付方式、支付密码等等（功能暂未实现）。界面如下：

<div align = "center">  
    <img width="200px" height="400px" src="pics/240f3c91-d82f-46a7-b53a-797e93d64e7a.png" />
</div>
<div align = "center"> 图 4-28 通用设置界面 </div><br>

### 4.7.8 个人信息

​	在我的页面中点击用户头像可进入个人信息页面，该页面显示这用户的基本信息，用户也可对这些信息如头像进行修改。界面如下：

<div align = "center">  
    <img width="200px" height="400px" src="pics/9ce44cdf-fac5-439f-90bd-992c3b0d9ddc.png" />
    <img width="200px" height="400px" src="pics/d1d43da4-b984-4a84-865f-2e3509bb3927.png" />
    <img width="200px" height="400px" src="pics/14e5ba60-6556-427b-90da-268ed71893dd.png" />
</div>
<div align = "center"> 图 4-29 个人信息界面 </div><br>

## 4.8 订单

### 4.8.1 全部订单

​	用户在我的页面点击全部订单进入到对应的订单页，默认选中全部订单，左右滑动可切换订单类型。该界面中订单按待付款、待使用、退款售后、待评价、已完成排序，点击订单条目可跳到对应的订单详情页。已完成订单可长按删除，也可点击再来一单进入确认订单页。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/e168dbf6-9691-459d-8f1b-ff5ca82619bf.png" />
    <img width="200px" height="400px" src="pics/3b787c2b-719e-49e4-a2b5-d059e0443d1c.png" />
</div>
<div align = "center"> 图 4-30 全部订单界面 </div><br>

### 4.8.2 待付款

​	用户在我的页面点击待付款进入到对应的订单页，默认选中待付款，左右滑动可切换订单类型。该界面按订单时间降序，点击去付款则可以使用支付宝进行付款，付款成功后订单状态变为待使用。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/16431676-053e-40c7-b7bc-544c84a9b7e0.png" />
    <img width="200px" height="400px" src="pics/3df15f36-4ca8-4706-80b9-d2c2602f6dd7.png" />
</div>
<div align = "center"> 图 4-31 待付款界面 </div><br>

### 4.8.3 待使用

​	用户在我的页面点击待使用进入到对应的订单页，默认选中待使用，左右滑动可切换订单类型。该界面按订单时间降序，点击进入使用订单详情页，商家扫描二维码则可以使用订单，订单使用后状态变为待评价。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/2aa1da7b-4f79-4e1f-9da3-51977791a656.png" />
    <img width="200px" height="400px" src="pics/9c258585-2ccf-45df-b6ea-474a3b7450cf.png" />
</div>
<div align = "center"> 图 4-32 待使用界面 </div><br>

### 4.8.4 待评价

​	用户在我的页面点击待评价进入到对应的订单页，默认选中待评价，左右滑动可切换订单类型。该界面按订单时间降序，点击进入订单详情页，点击去评价则进入订单评价页，在此页面选择评价星星、填写评价内容和评价图片则可以完成评价，完成评价后订单状态变为已完成。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/a3d1b7bc-a112-4efc-8cbc-50c6f133d350.png" />
    <img width="200px" height="400px" src="pics/6e7d9a60-7ba8-43ba-baca-b4e7ae7518e1.png" />
    <img width="200px" height="400px" src="pics/2236c34d-0157-4a58-802d-07b377ce3883.png" />
</div>
<div align = "center"> 图 4-33 待评价界面 </div><br>

### 4.8.5 退款/售后

​	用户在我的页面点击退款/售后进入到对应的订单页，默认选中退款/售后，左右滑动可切换订单类型。该界面按订单时间降序，点击进入退款售后详情页。界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/f20611be-7920-478c-8718-0a0c45d4b280.png" />
    <img width="200px" height="400px" src="pics/b26df1ae-8ed2-40ad-abfc-6365e556763e.png" />
</div>
<div align = "center"> 图 4-34 退款售后详情界面 </div><br>

​	在已完成订单详情中点击申请售后，可发起售后服务，选择退款原因、上传凭证等理由则可以提交售后申请，界面如图：

<div align = "center">  
    <img width="200px" height="400px" src="pics/7032b765-d09a-4b61-8bf6-714c0987d40b.png" />
    <img width="200px" height="400px" src="pics/327e3589-7e0f-4f26-9fb8-ce9c8ca09b9a.png" />
    <img width="200px" height="400px" src="pics/b71c59b5-3158-4cf3-8aa6-1d27554e0951.png" />
</div>
<div align = "center"> 图 4-35 提交售后界面 </div><br>
