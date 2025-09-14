このプロジェクトは、Android・iOS・デスクトップ（JVM）を対象とした Kotlin Multiplatform プロジェクトです。

また、本アプリケーションはオンデバイス LLM を利用して、RSS で取得した記事をユーザーの興味・関心にあった形で表示することを目指しています。

* [/composeApp](./composeApp/src) には、Compose Multiplatform アプリ間で共有されるコードが含まれます。
  次のようなサブフォルダがあります:
  - [commonMain](./composeApp/src/commonMain/kotlin) すべてのターゲットに共通のコードを配置します。
  - そのほかの各フォルダには、フォルダ名で示されたプラットフォーム専用にコンパイルされる Kotlin コードを配置します。
    例えば、iOS で Apple の CoreCrypto を使いたい場合は、[iosMain](./composeApp/src/iosMain/kotlin) フォルダに記述します。
    同様に、デスクトップ（JVM）固有のコードは [jvmMain](./composeApp/src/jvmMain/kotlin) フォルダに記述します。

* [/iosApp](./iosApp/iosApp) には iOS アプリケーションが含まれます。Compose Multiplatform で UI を共有していても、
  iOS アプリのエントリポイントとしてこのモジュールが必要です。SwiftUI のコードを追加する場合もここに配置します。

### Android アプリのビルドと実行

開発版の Android アプリをビルド・実行するには、IDE のツールバーにある実行構成（Run Configuration）を使用するか、
ターミナルから次のコマンドを実行します:
- macOS/Linux の場合
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- Windows の場合
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### デスクトップ（JVM）アプリのビルドと実行

開発版のデスクトップアプリをビルド・実行するには、IDE の実行構成を使用するか、
ターミナルから次のコマンドを実行します:
- macOS/Linux の場合
  ```shell
  ./gradlew :composeApp:run
  ```
- Windows の場合
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### iOS アプリのビルドと実行

開発版の iOS アプリをビルド・実行するには、IDE の実行構成を使用するか、[/iosApp](./iosApp) ディレクトリを Xcode で開いて実行してください。

---

[Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) の詳細はこちらをご覧ください。