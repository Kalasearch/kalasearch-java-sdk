# 卡拉搜索 - JAVA SDK (kalasearch-java-sdk)

卡拉搜索提供托管的API服务，5分钟即可帮助你的App、小程序、网站接入极速智能的搜索功能
- [卡拉搜索 - Java SDK (kalasearch-java-sdk)](#卡拉搜索 - JAVA SDK (kalasearch-java-sdk))
  - [功能简介](#功能简介)
  - [🎬开始使用](#🎬开始使用)
  - [💻常见操作](#💻常见操作)
  - [深入理解搜索引擎](#深入理解搜索引擎)
  - [参与贡献](#参与贡献)  
  - [LICENSE](#license)
## 功能简介

卡拉搜索提供对开发友好的API，同时也对各语言封装了SDK。在卡拉搜索的java SDK中，我们提供简单的接口，而你也可以在后台对于自己的搜索和搜索排序需求进行深度配置。更多关于卡拉搜索的信息，请参考：[为什么要用卡拉搜索](https://kalasearch.cn/docs#%E4%B8%BA%E4%BB%80%E4%B9%88%E8%A6%81%E7%94%A8%E5%8D%A1%E6%8B%89%E6%90%9C%E7%B4%A2)

## 🎬开始使用

请使用 `maven`, `gradle` 安装卡拉搜索的java SDK，目前支持java 8以上版本

```
<dependency>
    <groupId>com.kalasearch.client</groupId>
    <artifactId>kalasearch-core</artifactId>
    <version>${version}</version>
</dependency>
```

在导入java sdk后，即可开始索引对象和搜索对象。我们以豆瓣电影数据为例，说明如何打造一个简单的电影搜索引擎

```
// 传入你的AppId和API Key，可在卡拉搜索网站获得
KalasearchClient kalasearchClient = new KalasearchClient("YOUR AppId", "YOUR ApiKey");

// 传入你的搜索索引的id，同样可在卡拉搜索网站获得
Index index = kalasearchClient.getIndex("your index");

// 添加第一部电影
TestDocument testDocument = TestDocument.builder().name("大话西游").actors("周星驰/吴孟达").year("2000").build();
Optional<RespEntity<TestDocument>> optional = index.addObject(testDocument);
if (optional.isPresent()) {
    RespEntity<TestDocument> indexResp = optional.get();
}

// 用关键词进行搜索
QueryInfo queryInfo = QueryInfo.builder().query("孟达").build();
Optional<RespEntity<TestDocument>> optional = index.search(queryInfo);
if (optional.isPresent()) {
    RespEntity<TestDocument> indexResp = optional.get();
}

// and more
```

关于如何获得`AppId`, `ApiKey` 和 `indexId`，请参考[体验卡拉搜索](https://kalasearch.cn/docs/try-kalasearch)

## 💻常见操作

卡拉搜索的所有数据接口均为在[卡拉搜索REST-API](https://kalasearch.cn/docs/rest-api)上的封装，因此所有的操作与REST API中记录的一致。具体来说，本java SDK中有如下操作

### 添加对象
`index.addObject(object)` 
| 参数名 | 说明 |
| ------| ----|
| object| 数据对象 |


```
TestDocument testDocument = TestDocument.builder().name("大话西游").actors("周星驰/吴孟达").year("2000").build();
Optional<RespEntity<TestDocument>> optional = index.addObject(testDocument);
```

返回值: 

```
{'_id': 'wQawuHIBeKV8--CRsu-u'}
```

### 搜索对象
```
QueryInfo queryInfo = QueryInfo.builder().queryw("孟达").build();
RespEntity queryResp = index.search(queryInfo);
```
| QueryInfo参数名 | 说明 |
| ------| ----|
| query| 搜索字符串，如`大话西游`|
| searchOptions| 可选搜索参数，默认为空 |



## 深入理解搜索引擎
如果你对搜索技术感兴趣，或者单纯希望为用户搭建一个体验更好的搜索系统，请关注卡拉搜索的技术博客[http://kalasearch.cn/blog](http://kalasearch.cn/blog)。

## 参与贡献

用idea导入项目,工程使用maven构建,需要安装lombok插件

## LICENSE

卡拉搜索java-sdk为完全开源软件，同时遵循MIT license
