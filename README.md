# 农贸市场管理系统

[![Android CI](https://github.com/qq00150610-cpu/market-manager/actions/workflows/android.yml/badge.svg)](https://github.com/qq00150610-cpu/market-manager/actions/workflows/android.yml)

一个完整的农贸市场管理系统 Android 应用，帮助市场管理者高效管理商户、用户、商品、订单和团购活动。

## 📱 功能概览

### ✅ 全部功能已开发完成

| 模块 | 功能 | 状态 |
|------|------|------|
| **用户认证** | 登录、注册、密码管理、表单验证 | ✅ 完成 |
| **首页仪表盘** | 欢迎信息、统计卡片、快速操作、订单查看 | ✅ 完成 |
| **市场管理** | 商户列表、搜索筛选、状态管理、添加商户 | ✅ 完成 |
| **商户/商品管理** | 商品列表、分类筛选、库存管理、搜索、添加商品 | ✅ 完成 |
| **团购管理** | 团购列表、状态筛选、进度显示、创建团购 | ✅ 完成 |
| **用户管理** | 用户列表、角色筛选、搜索、添加用户 | ✅ 完成 |
| **订单管理** | 订单列表、状态筛选、订单详情、状态更新 | ✅ 完成 |
| **个人中心** | 用户信息、统计数据、功能菜单、系统设置、退出登录 | ✅ 完成 |
| **数据统计** | 收入统计、商户统计、品类分布、每日收入趋势 | ✅ 完成 |
| **消息通知** | 通知列表、类型分类、已读/未读、全部标记已读 | ✅ 完成 |
| **营销工具** | 优惠券、满减活动、限时抢购、新用户优惠、进度追踪 | ✅ 完成 |

## 📸 屏幕模块

- **登录界面** - 邮箱/密码登录，表单验证
- **注册界面** - 完整注册流程
- **主界面** - 底部导航栏（首页、市场、商户、团购、用户、我的）
- **市场管理** - 商户列表 + 状态管理
- **商户管理** - 商品列表 + 库存管理
- **团购管理** - 团购活动 + 进度展示
- **用户管理** - 用户列表 + 角色管理
- **订单管理** - 订单列表 + 状态更新
- **个人中心** - 信息展示 + 功能入口
- **数据统计** - 收入/商户/品类统计
- **消息通知** - 通知中心
- **营销工具** - 促销活动管理

## 🛠 技术栈

| 技术 | 版本 |
|------|------|
| 开发语言 | Kotlin 1.9.22 |
| UI框架 | Jetpack Compose + Material 3 |
| 架构模式 | MVVM (Model-View-ViewModel) |
| 状态管理 | StateFlow + Compose State |
| 导航 | Navigation Compose |
| 最低SDK | Android 8.0 (API 26) |
| 目标SDK | Android 14 (API 34) |

## 📁 项目结构

```
MarketManager/
├── app/src/main/java/com/example/marketmanager/
│   ├── MainActivity.kt                   # 主Activity
│   ├── navigation/
│   │   └── AppNavigation.kt              # 导航配置
│   ├── data/
│   │   ├── models/                        # 数据模型
│   │   │   ├── Merchant.kt               # 商户模型
│   │   │   ├── Product.kt                # 商品模型
│   │   │   ├── User.kt                   # 用户模型
│   │   │   ├── Order.kt                  # 订单模型
│   │   │   ├── GroupBuy.kt               # 团购模型
│   │   │   └── Statistics.kt             # 统计/通知/促销/市场模型
│   │   └── MockData.kt                   # 模拟数据
│   ├── viewmodel/
│   │   └── MainViewModel.kt              # 主ViewModel
│   └── ui/
│       ├── theme/                        # 主题（Color/Theme/Type）
│       └── screens/
│           ├── auth/                     # 登录/注册
│           ├── main/                     # 主界面首页
│           ├── market/                   # 市场管理
│           ├── merchant/                 # 商户管理
│           ├── groupbuy/                 # 团购管理
│           ├── user/                     # 用户管理
│           ├── order/                    # 订单管理
│           ├── profile/                  # 个人中心
│           ├── stats/                    # 数据统计
│           ├── notification/             # 消息通知
│           └── promotion/                # 营销工具
├── .github/workflows/android.yml        # GitHub Actions（双版本构建）
└── 文档文件
```

## 🚀 构建与部署

### GitHub Actions 自动构建
每次推送到 `main` 分支会自动触发构建：
- **Debug 版本** - 用于测试和调试
- **Release 版本** - 用于正式发布

### 下载构建产物
1. 访问 [GitHub Actions 页面](https://github.com/qq00150610-cpu/market-manager/actions)
2. 选择最近的构建运行
3. 在 "Artifacts" 部分下载 APK 文件

### 手动触发构建
在 GitHub Actions 页面选择 "Android CI" → "Run workflow" 即可手动触发。

## 📊 数据模型

### 核心实体
- **商户 (Merchant)** - 名称、负责人、摊位号、分类、状态
- **商品 (Product)** - 名称、分类、价格、库存、所属商户
- **用户 (User)** - 姓名、邮箱、角色（管理员/商户/消费者）
- **订单 (Order)** - 商品明细、金额、状态、支付方式
- **团购 (GroupBuy)** - 团购价、进度、时间范围

### 扩展实体
- **通知 (Notification)** - 类型、标题、内容、已读状态
- **促销 (Promotion)** - 优惠券、满减、限时抢购
- **市场 (Market)** - 多市场支持、摊位管理
- **统计数据 (DailyStats/RevenueStats)** - 收入/商户/品类统计

## 💰 盈利模式

| 收入来源 | 说明 |
|---------|------|
| SaaS服务费 | 基础版免费 / 专业版99元/月 / 企业版299元/月 |
| 交易抽成 | 团购交易抽成1-3% |
| 广告收入 | 商户推广广告、商品推荐广告 |
| 增值服务 | 数据分析报告、营销工具、优先客服 |

## 📜 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 👤 账户信息

> ⚠️ 机密信息存储在 `secret.md` 文件中（已添加到 `.gitignore`，不会上传到仓库）

- **GitHub**: qq00150610-cpu
- **仓库**: https://github.com/qq00150610-cpu/market-manager
