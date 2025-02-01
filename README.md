# Stateful Kotlin Telegram Bot Starter

Spring Boot starter with buildin [Java Telegram Bot API](https://github.com/pengrad/java-telegram-bot-api)

Features:
* Kotlin-style API methods for some of [available methods](https://core.telegram.org/bots/api#available-methods)
* Markdown methods for [String Templates](https://kotlinlang.org/docs/strings.html#string-templates)

## Usage
Add `sktb-spring-boot-starter` dependency

* Gradle with Kotlin DSL

  Add GitHub Packages repository:
    ```kotlin
    maven {
        name = "sktb-repository"
        url = uri("https://maven.pkg.github.com/anfanik/sktb-spring-boot-starter")

        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
    ```

  Add `sktb-spring-boot-starter` dependency:
    ```kotlin
    implementation("me.anfanik:sktb-spring-boot-starter:8.1.0.0")
    ```

## Examples

You can use TelegramService – a TelegramBot wrapper with API method-specific implemented functions like `sendMessage`:

```kotlin
telegram.sendMessage(chatId = "anfanik", text = "Hello ${"world".bold()}!") {
    parseMode(ParseMode.HTML)
}
```

or general `execute` function for not-implemented API methods:

```kotlin
telegram.execute(SendMessage("anfanik", "Hello ${"world".bold()}!")) {
    parseMode(ParseMode.HTML)
}
```