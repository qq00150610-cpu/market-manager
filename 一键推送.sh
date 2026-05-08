#!/bin/bash

# 农贸市场管理系统 - 一键推送脚本

echo "=========================================="
echo "   农贸市场管理系统 - 一键推送脚本"
echo "=========================================="
echo ""

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 检查是否在正确的目录
if [ ! -f "build.gradle" ]; then
    echo -e "${RED}错误: 请在MarketManager目录中运行此脚本${NC}"
    exit 1
fi

# 检查Git状态
echo -e "${YELLOW}检查Git状态...${NC}"
if [ -d ".git" ]; then
    echo -e "${GREEN}✓ Git仓库已初始化${NC}"
else
    echo -e "${RED}✗ Git仓库未初始化${NC}"
    exit 1
fi

# 检查是否有未提交的更改
if [ -n "$(git status --porcelain)" ]; then
    echo -e "${YELLOW}发现未提交的更改，正在提交...${NC}"
    git add .
    git commit -m "自动提交：所有功能模块开发完成"
    echo -e "${GREEN}✓ 代码已提交${NC}"
else
    echo -e "${GREEN}✓ 所有代码已提交${NC}"
fi

echo ""
echo -e "${YELLOW}准备推送到GitHub...${NC}"
echo ""

# 检查是否已设置远程仓库
REMOTE_URL=$(git remote get-url origin 2>/dev/null)
if [ -n "$REMOTE_URL" ]; then
    echo -e "${GREEN}✓ 远程仓库已设置: $REMOTE_URL${NC}"
    echo -e "${YELLOW}正在推送代码...${NC}"
    git push -u origin main
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ 代码推送成功！${NC}"
    else
        echo -e "${RED}✗ 代码推送失败${NC}"
        exit 1
    fi
else
    echo -e "${YELLOW}未设置远程仓库，正在创建GitHub仓库...${NC}"
    
    # 检查是否安装了GitHub CLI
    if ! command -v gh &> /dev/null; then
        echo -e "${RED}错误: 未安装GitHub CLI${NC}"
        echo "请先安装GitHub CLI: https://cli.github.com/"
        echo ""
        echo "或者手动操作："
        echo "1. 在GitHub上创建新仓库: https://github.com/new"
        echo "2. 仓库名称: market-manager"
        echo "3. 选择公开（Public）"
        echo "4. 不要初始化README、.gitignore或LICENSE"
        echo "5. 运行以下命令："
        echo "   git remote add origin https://github.com/你的用户名/market-manager.git"
        echo "   git push -u origin main"
        exit 1
    fi
    
    # 检查是否已登录GitHub CLI
    if ! gh auth status &> /dev/null; then
        echo -e "${RED}错误: 未登录GitHub CLI${NC}"
        echo "请先登录GitHub CLI:"
        echo "gh auth login"
        echo ""
        echo "登录完成后，重新运行此脚本"
        exit 1
    fi
    
    # 创建GitHub仓库
    echo -e "${YELLOW}正在创建GitHub仓库: market-manager${NC}"
    gh repo create market-manager --public --source=. --remote=origin --push
    
    if [ $? -eq 0 ]; then
        echo ""
        echo -e "${GREEN}✓ GitHub仓库创建成功！${NC}"
        echo -e "${GREEN}✓ 代码推送成功！${NC}"
        echo ""
        
        # 获取仓库URL
        REPO_URL=$(git remote get-url origin)
        echo -e "${GREEN}仓库地址: $REPO_URL${NC}"
        echo ""
        echo -e "${YELLOW}接下来：${NC}"
        echo "1. 访问GitHub仓库查看代码"
        echo "2. GitHub Actions将自动开始构建"
        echo "3. 构建完成后，下载APK进行测试"
        echo ""
        echo -e "${GREEN}构建状态徽章已添加到README.md${NC}"
    else
        echo -e "${RED}✗ GitHub仓库创建失败${NC}"
        echo ""
        echo "请尝试手动操作："
        echo "1. 在GitHub上创建新仓库: https://github.com/new"
        echo "2. 仓库名称: market-manager"
        echo "3. 选择公开（Public）"
        echo "4. 不要初始化README、.gitignore或LICENSE"
        echo "5. 运行以下命令："
        echo "   git remote add origin https://github.com/你的用户名/market-manager.git"
        echo "   git push -u origin main"
        exit 1
    fi
fi

echo ""
echo -e "${GREEN}==========================================${NC}"
echo -e "${GREEN}   推送完成！${NC}"
echo -e "${GREEN}==========================================${NC}"
echo ""
echo -e "${YELLOW}下一步操作：${NC}"
echo "1. 访问GitHub仓库查看代码"
echo "2. 查看GitHub Actions构建状态"
echo "3. 下载APK进行测试"
echo "4. 继续开发新功能"
echo ""
echo -e "${GREEN}所有构建都在GitHub中进行，本地只负责代码编写！${NC}"
echo ""