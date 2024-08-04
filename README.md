# Reminder

## Requirement
動作環境:  
openjdk 18 2022-03-22

## How to Run
1. Java ソースコードをコンパイルする
   ```bash
   javac ReversePolishCalculator/Main.java
   ```
2. プログラムを実行する
   ```bash
   java -cp ReversePolishCalculator.Main
   ```

## Usage
### 概要
予定時刻管理機能付きのスケジュール帳です。  
予定時刻(年月日・時・分)とタイトル、詳細説明で構成されるスケジュールを一覧表示・追加・削除できます。

### 基本的な使い方
設定した予定は画面に一覧表示されます。  
予定の時刻を経過すると、その予定は赤文字で表示されます。  
経過した予定の数は、画面上部に表示されます。
予定のチェックボックスをクリックすると、その予定は実行済みとなり削除されます。

### 予定の追加
画面上部の "+" ボタンをクリックすると「予定を追加」ウィンドウが開き、予定を追加できます。  
予定時刻とタイトル、詳細説明を設定し、「予定を作成」をクリックすることで予定を作成できます。

## Lisence
This project is licensed under the MIT License.
