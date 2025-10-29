# 🎬 RussianCinematic

<div align="center">

[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)](https://github.com/gordey9992/RussianCinematic/releases/)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.8-purple.svg)](https://www.minecraft.net/)
[![Ядро](https://img.shields.io/badge/Purpur-1.21.8-purple.svg)](https://purpurmc.org/download/purpur/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://mit-license.org/)
[![AIPlugin](https://img.shields.io/badge/AI_Minecraft-Plugin-purple.svg)](https://github.com/gordey9992/AI-Minecraft-Plugin/releases/)

**Мощный плагин для катсцен и Telegram-интеграции**

*Сделано с ❤️ для русского комьюнити Minecraft*

</div>

## ✨ Возможности

### 🎭 Система катсцен
- 🆕 Автоматические сцены для новых игроков
- 🤖 Интерактивные NPC с диалогами на русском языке
- 🎮 Блокировка управления во время катсцен
- ⏩ Плавные переходы между репликами
- ⚙️ Настраиваемые диалоги через YAML-конфиги

### 🤖 Telegram интеграция
- 🔔 Уведомления о входе/выходе игроков
- 💬 Синхронизация чата в реальном времени
- 📱 Форматированные сообщения:
  - 🎮 `{ник} » {сообщение}` - игровой чат
  - `+ {ник}` - игрок зашел
  - `― {ник}` - игрок вышел
  - `Сервер включен!` - статус сервера

## ⚙️ Технические особенности
- ✅ Полная настройка через конфигурационные файлы
- ✅ Поддержка EssentialsXChat
- ✅ Оптимизация для Purpur 1.21.8
- ✅ Система прав и разрешений
- ✅ Логирование и отладка
- ✅ API для разработчиков

## 🚀 Быстрый старт

### Установка
1. Скачать `RussianCinematic.jar` из Releases
2. Поместить в папку `plugins/`
3. Перезагрузить сервер: `/reload`
4. Настроить `config.yml`

### Настройка Telegram
```yaml
telegram:
  bot-token: "123456:ABC-DEF..."
  chat-id: "-1001234567890"
  enabled: true
```

🎮 Команды
Команда	Описание	Права
/cinematic start	Начать катсцену	russiancinematic.cinematic.start
/cinematic stop	Остановить катсцену	russiancinematic.cinematic.stop
/telegram reload	Перезагрузить конфиги	russiancinematic.telegram.reload
/telegram status	Статус бота	russiancinematic.telegram.status

📁 Структура проекта
```RussianCinematic/
├── src/main/java/com/gordey9992/russiancinematic/
│   ├── RussianCinematic.java
│   ├── TelegramBot.java
│   ├── CinematicManager.java
│   ├── CinematicCommand.java
│   └── TelegramCommand.java
├── src/main/resources/
│   ├── plugin.yml
│   ├── config.yml
│   └── messages.yml
└── pom.xml
```

🔧 Разработка
```git clone https://github.com/gordey9992/RussianCinematic
cd RussianCinematic
mvn clean package
📞 Поддержка
🐛 GitHub Issues: для багов и предложений
```

<div align="center">
RussianCinematic - сделано с ❤️ для русского комьюнити Minecraft!
Погружение в историю начинается здесь! 🎭

</div> EOF echo "✅ Создан README.md" ```
