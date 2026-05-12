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
# 消费记录App

## 项目简介
本项目是一个基于 Java 的 Android 消费记录管理 App，用于记录日常消费支出，支持添加、查看、删除消费记录，并提供月度统计、通知提醒等功能。

## 主要功能
1. 主界面显示本月总消费金额
2. 添加消费记录（金额、类别、备注、日期）
3. 显示消费记录列表（RecyclerView）
4. 删除消费记录（滑动删除）
5. 查看所有记录详情页面
6. 消费分类（餐饮、交通、购物、娱乐、其他）
7. 本地数据库持久化存储（ContentProvider + SQLite）
8. 后台服务监控消费情况，发送通知提醒
9. 广播接收器更新界面数据

## 使用技术
1. Java
2. Android Studio
3. XML Layout
4. RecyclerView
5. SQLite Database
6. ContentProvider
7. Service（后台服务）
8. BroadcastReceiver
9. Notification
10. ViewModel
11. Git

## 项目结构
```
app/src/main/java/com/example/class_two/
├── MainActivity.java                  # 主页面，展示本月消费和记录列表
├── RecordListActivity.java            # 所有记录列表页面
├── FeaturesTestActivity.java          # 功能测试页面
├── NotificationTestActivity.java      # 通知测试页面
├── adapter/
│   ├── TransactionAdapter.java        # 消费记录适配器
│   ├── RecordAdapter.java             # 记录适配器
│   └── RecordViewHolder.java          # 记录列表视图持有者
├── model/
│   ├── Transaction.java               # 消费数据实体类
│   ├── RecordItem.java                # 记录项实体类
│   ├── RecordType.java                # 记录类型枚举
│   └── RecordDataProvider.java        # 数据提供者
├── provider/
│   ├── ExpenseProvider.java           # ContentProvider 内容提供者
│   ├── ExpenseDatabaseHelper.java     # 数据库帮助类
│   └── ExpenseContract.java           # 数据库合约类
├── repository/
│   └── TransactionRepository.java     # 数据仓库层
├── viewmodel/
│   └── ExpenseViewModel.java          # ViewModel 数据管理
├── service/
│   └── ExpenseMonitorService.java     # 消费监控后台服务
├── receiver/
│   └── ExpenseUpdateReceiver.java     # 广播接收器
└── utils/
    ├── BroadcastConstants.java        # 广播常量
    └── NotificationHelper.java        # 通知帮助类

app/src/main/res/
├── layout/                            # 页面布局文件
│   ├── activity_main.xml
│   ├── activity_record_list.xml
│   ├── activity_features_test.xml
│   ├── activity_notification_test.xml
│   ├── dialog_add_transaction.xml
│   ├── item_record.xml
│   └── transaction_item.xml
├── drawable/                          # 图标和背景资源
│   ├── btn_rounded_corner.xml
│   ├── btn_rounded_corner_secondary.xml
│   ├── card_background.xml
│   ├── ic_add.xml
│   ├── ic_notification.xml
│   ├── ic_launcher_background.xml
│   └── ic_launcher_foreground.xml
├── values/                            # 颜色、字符串、主题
│   ├── colors.xml
│   ├── strings.xml
│   └── themes.xml
└── xml/                               # 备份规则
    ├── backup_rules.xml
    └── data_extraction_rules.xml
```

## 运行方式
1. 使用 Android Studio 打开项目；
2. 等待 Gradle 同步完成；
3. 连接模拟器或真机设备（最低支持 Android 5.0 API 24）；
4. 点击 Run 运行项目，或按 Shift+F10；
5. 构建成功后 APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。

## 开发过程说明
本项目使用 Git 进行版本管理，主要提交记录包括：
1. 创建 Android 项目，配置 Gradle 和基础依赖；
2. 完成主页面布局（activity_main.xml），包含总消费金额展示区和记录列表；
3. 添加数据模型（Transaction、RecordItem、RecordType）；
4. 实现消费记录列表显示（RecyclerView + Adapter）；
5. 实现添加消费功能（对话框输入）；
6. 实现滑动删除功能；
7. 集成 SQLite 数据库（ContentProvider + ExpenseDatabaseHelper）；
8. 实现后台服务（ExpenseMonitorService）监控消费并发送通知；
9. 实现广播接收器（ExpenseUpdateReceiver）更新界面；
10. 添加功能测试页面和通知测试页面；
11. 修复问题并完善 README 文档。

## 作者信息
- 姓名：秦超毅
- 班级：数字媒体技术一班
- 学号：23120031020

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