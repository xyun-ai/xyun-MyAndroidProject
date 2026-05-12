# 消费记录App

一个简单的消费记录管理应用，使用传统的Android XML布局和Java开发。

## 功能特点

1. **主界面显示总消费金额**
   - 顶部显示本月总消费金额
   - 清晰的金额显示格式

2. **添加消费功能**
   - 中间有醒目的添加消费按钮
   - 按钮带加号图标和按压效果

3. **消费记录列表**
   - 下方显示所有消费记录
   - 显示记录数量统计
   - 空列表时显示友好提示

## 技术栈

- **开发语言**: Java
- **UI框架**: 传统XML布局
- **最低SDK**: Android 5.0 (API 24)
- **目标SDK**: Android 13 (API 36)
- **不使用**: Kotlin, Jetpack Compose

## 项目结构

```
app/src/main/
├── res/
│   ├── layout/
│   │   └── activity_main.xml          # 主界面布局
│   └── drawable/
│       ├── btn_rounded_corner.xml     # 圆角按钮背景
│       └── ic_add.xml                 # 添加图标
├── java/
│   └── com/example/class_two/
│       └── MainActivity.java          # Java主活动
└── AndroidManifest.xml                # 应用清单
```

## 运行方法

1. 在Android Studio中打开项目
2. 等待Gradle同步完成
3. 连接Android设备或启动模拟器
4. 点击运行按钮 ▶️ 或按 Shift+F10

## 界面预览

主界面包含三个主要部分：

1. **顶部区域**: 绿色背景，显示总消费金额
2. **中间区域**: 添加消费按钮
3. **底部区域**: 消费记录列表

## 构建状态

✅ 项目已成功构建
✅ APK已生成: `app/build/outputs/apk/debug/app-debug.apk`

## 后续开发建议

1. 添加消费记录详情页面
2. 实现数据持久化（SQLite或Room）
3. 添加分类统计功能
4. 实现数据导出功能
5. 添加图表展示消费趋势

## 许可证

MIT License