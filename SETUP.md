# 农贸市场管理系统 - 设置指南

## ✅ 项目状态

项目已全部开发完成，并且已成功通过 GitHub Actions 构建（Debug + Release 双版本）。

**仓库地址**: https://github.com/qq00150610-cpu/market-manager

## 📦 获取 APK 安装包

### 方法一：从 GitHub Actions 下载
1. 访问 [GitHub 仓库 Actions 页面](https://github.com/qq00150610-cpu/market-manager/actions)
2. 选择最近一次成功构建的运行
3. 在 "Artifacts" 部分下载：
   - `app-debug` - 调试版本 APK
   - `app-release` - 发布版本 APK

### 方法二：手动触发构建
1. 访问 [GitHub 仓库 Actions 页面](https://github.com/qq00150610-cpu/market-manager/actions)
2. 选择 "Android CI" 工作流
3. 点击 "Run workflow" → "Run workflow"
4. 等待构建完成，下载产物

## 📱 安装与测试

1. 将下载的 APK 文件传输到 Android 设备
2. 在设备设置中允许"安装未知来源应用"
3. 安装 APK 文件
4. 启动应用，测试所有功能模块

## 🛠 本地开发（代码编辑）

### 前置条件
- Android Studio Hedgehog (2023.1.1) 或更高版本
- Android SDK 34
- JDK 17

### 克隆项目
```bash
git clone https://github.com/qq00150610-cpu/market-manager.git
cd market-manager
```

### 在 Android Studio 中打开
1. 启动 Android Studio
2. 选择 "Open" → 选择 `market-manager` 目录
3. 等待 Gradle 同步完成

### ⚠️ 重要说明
- **本地只负责代码编辑**，不在本地进行构建
- 所有构建通过 GitHub Actions 自动完成
- 修改代码后推送到 GitHub 即可触发自动构建

## 📂 项目结构

```
MarketManager/                          # 项目根目录
├── .github/workflows/
│   └── android.yml                     # GitHub Actions 构建配置
├── app/                                # Android 应用模块
│   ├── src/main/
│   │   ├── java/com/example/marketmanager/
│   │   │   ├── MainActivity.kt         # 入口 Activity
│   │   │   ├── navigation/
│   │   │   │   └── AppNavigation.kt    # 导航配置
│   │   │   ├── data/
│   │   │   │   ├── models/             # 8个数据模型
│   │   │   │   └── MockData.kt         # 模拟数据
│   │   │   ├── viewmodel/
│   │   │   │   └── MainViewModel.kt    # 主 ViewModel
│   │   │   └── ui/
│   │   │       ├── theme/              # Material3 主题
│   │   │       └── screens/            # 11个屏幕模块
│   │   └── res/                        # 资源文件
│   └── build.gradle                    # 模块构建配置
├── build.gradle                        # 项目构建配置
├── settings.gradle                     # 项目设置
├── gradle.properties                   # Gradle 属性
├── README.md                           # 项目说明
├── SETUP.md                            # 本文件
├── PROJECT_SUMMARY.md                  # 项目总结
├── 下一步操作.md                        # 操作指南
├── 开发完成报告.md                       # 开发报告
└── LICENSE                             # MIT 许可证
```

## 🔧 已配置的 GitHub Actions

工作流文件: `.github/workflows/android.yml`

**触发条件**:
- 推送到 `main` 分支
- 推送到 `develop` 分支
- Pull Request 到 `main` 分支
- 手动触发 (workflow_dispatch)

**构建流程**:
1. 检出代码
2. 配置 JDK 17
3. 构建 Debug APK (`./gradlew assembleDebug`)
4. 构建 Release APK (`./gradlew assembleRelease`)
5. 上传两个 APK 到 Artifacts

## 📊 功能模块清单

### 基础功能
- ✅ 用户登录/注册
- ✅ 主界面仪表盘（统计卡片、快速操作）
- ✅ 底部导航栏（6个标签页）

### 核心管理
- ✅ 市场管理（商户列表、搜索、筛选）
- ✅ 商户/商品管理（商品列表、分类、库存）
- ✅ 用户管理（列表、角色筛选）
- ✅ 订单管理（列表、状态更新）
- ✅ 团购管理（活动列表、进度展示）

### 增值功能
- ✅ 个人中心（信息、统计、菜单、退出登录）
- ✅ 数据统计（收入、商户、品类分析）
- ✅ 消息通知（类型分类、已读/未读）
- ✅ 营销工具（优惠券、满减、限时抢购）
- ✅ 多市场支持模型

## 🔐 账户信息

> ⚠️ 敏感信息存储在 `secret.md` 文件中（已添加到 `.gitignore`，不会上传到仓库）

## 📝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/NewFeature`)
3. 提交更改 (`git commit -m '添加新功能'`)
4. 推送到分支 (`git push origin feature/NewFeature`)
5. 创建 Pull Request

## ❓ 常见问题

### Q: 构建失败怎么办？
A: 检查 GitHub Actions 日志，确认代码语法正确。已确认项目当前版本构建通过。

### Q: 如何获取最新 APK？
A: 访问 GitHub Actions 页面，选择最近的成功构建，在 Artifacts 中下载。

### Q: 可以本地构建测试吗？
A: 按照规则，所有构建在 GitHub 中进行。你也可以在 Android Studio 中本地构建用于测试。
