# 农贸市场管理系统 - 设置指南

## 项目状态

✅ **已完成的工作：**
1. 项目框架搭建完成
2. 基础UI界面实现（登录、注册、主界面）
3. GitHub Actions配置完成（双版本构建：Debug和Release）
4. Git仓库初始化完成
5. 代码已提交到本地仓库

## 推送到GitHub

### 方法一：使用GitHub CLI（推荐）

1. **登录GitHub CLI**
   ```bash
   gh auth login
   ```

2. **创建GitHub仓库并推送代码**
   ```bash
   cd MarketManager
   gh repo create market-manager --public --source=. --remote=origin --push
   ```

### 方法二：手动操作

1. **在GitHub上创建新仓库**
   - 访问 https://github.com/new
   - 仓库名称：`market-manager`
   - 选择公开（Public）
   - 不要初始化README、.gitignore或LICENSE

2. **添加远程仓库并推送**
   ```bash
   cd MarketManager
   git remote add origin https://github.com/你的用户名/market-manager.git
   git push -u origin main
   ```

## 构建说明

### 自动构建
推送代码后，GitHub Actions会自动触发构建：
- 构建Debug版本
- 构建Release版本
- 两个APK都会作为构建产物上传

### 手动触发构建
在GitHub仓库页面：
1. 点击 "Actions" 选项卡
2. 选择 "Android CI" 工作流
3. 点击 "Run workflow"

## 下载构建产物

1. 在GitHub仓库页面，点击 "Actions" 选项卡
2. 选择最近的构建运行
3. 在 "Artifacts" 部分下载：
   - `app-debug.apk` - 调试版本
   - `app-release.apk` - 发布版本

## 项目结构

```
MarketManager/
├── .github/workflows/android.yml  # GitHub Actions配置
├── app/                           # Android应用模块
│   ├── src/main/                  # 源代码
│   ├── build.gradle              # 模块构建配置
│   └── proguard-rules.pro        # ProGuard规则
├── build.gradle                  # 项目构建配置
├── settings.gradle               # 项目设置
├── gradle.properties             # Gradle属性
├── gradle/wrapper/               # Gradle包装器
├── README.md                     # 项目说明
├── LICENSE                       # MIT许可证
└── .gitignore                    # Git忽略文件
```

## 功能模块

### 已实现
- ✅ 用户登录/注册
- ✅ 主界面框架
- ✅ 底部导航栏
- ✅ 首页仪表盘
- ✅ 统计卡片显示

### 开发中
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

## 注意事项

1. **本地不构建**：按照规则，所有构建都在GitHub中进行
2. **公开仓库**：项目默认使用公开仓库
3. **双版本构建**：每次构建都会生成Debug和Release两个版本
4. **构建产物**：APK文件可以在GitHub Actions的Artifacts中下载

## 故障排除

### 构建失败
1. 检查GitHub Actions日志
2. 确保Gradle配置正确
3. 检查依赖项版本兼容性

### 推送失败
1. 检查GitHub认证
2. 确认仓库权限
3. 检查网络连接

## 联系支持

如有问题，请通过以下方式联系：
- 项目Issues：在GitHub仓库中创建Issue
- 邮箱：[您的邮箱]