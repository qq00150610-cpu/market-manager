# 农贸市场管理系统

[![Android CI](https://github.com/qq00150610-cpu/market-manager/actions/workflows/android.yml/badge.svg)](https://github.com/qq00150610-cpu/market-manager/actions/workflows/android.yml)

一个完整的农贸市场管理系统Android应用，帮助市场管理者高效管理商户、用户、商品、订单和团购活动。

## 功能特性

### 已实现功能
- ✅ 用户登录/注册
- ✅ 主界面框架
- ✅ 底部导航栏
- ✅ 首页仪表盘
- ✅ 统计卡片显示

### 开发中功能
- 🔄 市场管理模块
- 🔄 商户管理模块
- 🔄 团购管理模块
- 🔄 个人中心模块
- 🔄 用户管理模块
- 🔄 订单管理模块

## 技术栈

- **开发语言**: Kotlin
- **UI框架**: Jetpack Compose
- **架构模式**: MVVM
- **导航**: Navigation Compose
- **主题**: Material Design 3
- **后端**: Firebase (计划中)
- **数据库**: Firestore (计划中)

## 项目结构

```
MarketManager/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/marketmanager/
│   │   │   ├── MainActivity.kt
│   │   │   ├── navigation/
│   │   │   │   └── AppNavigation.kt
│   │   │   └── ui/
│   │   │       ├── screens/
│   │   │       │   ├── auth/
│   │   │       │   │   ├── LoginScreen.kt
│   │   │       │   │   └── RegisterScreen.kt
│   │   │       │   └── main/
│   │   │       │       └── MainScreen.kt
│   │   │       └── theme/
│   │   │           ├── Color.kt
│   │   │           ├── Theme.kt
│   │   │           └── Type.kt
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## 编译运行

### 前提条件
- Android Studio Hedgehog (2023.1.1) 或更高版本
- Android SDK 34
- Kotlin 1.9.22

### 运行步骤
1. 使用Android Studio打开`MarketManager`目录
2. 等待Gradle同步完成
3. 连接Android设备或启动模拟器
4. 点击运行按钮编译安装

## 盈利模式

### 主要收入来源
1. **SaaS服务费**: 向商户收取月度/年度管理费
2. **交易抽成**: 从团购交易中抽取一定比例
3. **广告收入**: 在应用中展示商户广告
4. **增值服务**: 提供数据分析、营销工具等高级功能

### 定价策略
- 基础版: 免费 (功能受限)
- 专业版: 99元/月
- 企业版: 299元/月

## 开发计划

### 第一阶段 (当前)
- [x] 项目框架搭建
- [x] 登录注册功能
- [x] 主界面导航
- [ ] 基础数据模型

### 第二阶段
- [ ] 商户管理模块
- [ ] 商品管理模块
- [ ] 用户管理模块
- [ ] 基础团购功能

### 第三阶段
- [ ] 订单管理系统
- [ ] 支付集成
- [ ] 数据统计报表
- [ ] 消息通知系统

### 第四阶段
- [ ] 营销工具
- [ ] 数据分析
- [ ] 多市场支持
- [ ] 移动支付集成

## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 联系方式

- 项目维护者: [您的姓名]
- 邮箱: [您的邮箱]
- 项目链接: [项目GitHub地址]

## 致谢

感谢所有为这个项目做出贡献的人！