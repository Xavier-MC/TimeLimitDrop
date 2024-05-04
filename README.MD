# TimeLimitDrop

TimeLimitDrop 是一个 Minecraft 服务器插件，用于限制玩家在一定时间内死亡后掉落物品的功能。

## 功能特点

- 根据配置文件中设置的最大游戏时间，控制玩家死亡后是否掉落物品。
- 联动LuckPerms插件和AngelChest插件

## 支持版本

- 1.20及以上的Spigot核心及其下游分支。

## 依赖

- [LuckPerms](https://luckperms.net/)
- [AngelChest](https://www.spigotmc.org/resources/%E2%AD%90-angelchest-plus-%E2%AD%90-death-chests-graveyards.88214/)

## 安装

1. 下载插件的 JAR 文件。
2. 将 JAR 文件放入服务器的插件目录。
3. 重启服务器，插件将自动加载。

## 配置

在插件目录下的 `config.yml` 文件中可以进行插件的配置。

```yml
maxPlayTime: 60
```

- `maxPlayTime`：设置玩家最大游戏时间，单位为分钟。当玩家的游戏时间未达到该值时，在玩家死亡后系统将自动送回死亡掉落物。

## 开源协议

本插件采用 BSD 3-Clause License 开源协议。


