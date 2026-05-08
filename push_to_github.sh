#!/bin/bash

# 农贸市场管理系统 - GitHub推送脚本

echo "=== 农贸市场管理系统 - GitHub推送脚本 ==="
echo ""

# 检查是否安装了GitHub CLI
if ! command -v gh &> /dev/null; then
    echo "错误: 未安装GitHub CLI"
    echo "请先安装GitHub CLI: https://cli.github.com/"
    exit 1
fi

# 检查是否已登录GitHub CLI
if ! gh auth status &> /dev/null; then
    echo "请先登录GitHub CLI:"
    echo "gh auth login"
    echo ""
    echo "登录完成后，重新运行此脚本"
    exit 1
fi

# 设置仓库名称
REPO_NAME="market-manager"
VISIBILITY="public"

echo "准备创建GitHub仓库: $REPO_NAME"
echo "仓库可见性: $VISIBILITY"
echo ""

# 创建GitHub仓库
echo "正在创建GitHub仓库..."
gh repo create "$REPO_NAME" --public --source=. --remote=origin --push

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ 仓库创建成功！"
    echo ""
    echo "仓库地址: https://github.com/$(gh api user --jq .login)/$REPO_NAME"
    echo ""
    echo "代码已成功推送到GitHub！"
    echo ""
    echo "接下来："
    echo "1. 访问GitHub仓库查看代码"
    echo "2. GitHub Actions将自动开始构建"
    echo "3. 构建完成后，下载APK进行测试"
    echo ""
    echo "构建状态徽章已添加到README.md"
else
    echo ""
    echo "❌ 仓库创建失败"
    echo ""
    echo "请尝试手动操作："
    echo "1. 在GitHub上创建新仓库: https://github.com/new"
    echo "2. 仓库名称: $REPO_NAME"
    echo "3. 选择公开（Public）"
    echo "4. 不要初始化README、.gitignore或LICENSE"
    echo "5. 运行以下命令："
    echo "   git remote add origin https://github.com/你的用户名/$REPO_NAME.git"
    echo "   git push -u origin main"
fi