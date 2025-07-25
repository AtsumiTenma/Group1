# ユースケース 06： 振替申請を送信する

## 概要
生徒が欠席したレッスンについて，振替申請を作成・送信する．

## アクター
- 生徒

## 事前条件
- 生徒がシステムにログインしていること
- ログインした生徒が過去に欠席したレッスンがあること

## 事後条件
- 振替申請（希望する振替先の日程・時間）が第3希望まで送信される．

## トリガ―
- 生徒が，任意の画面上で「振替申請を行うボタン」を押下する．

## 基本フロー
1. 生徒は，画面上の「振替申請を行う」ボタンを押下する．
2. システムは，本日の年月日時を取得する．
3. システムは，ログイン中の生徒が過去に欠席したレッスン数を取得する．
4. システムは，振替申請の入力フォーム（希望する振替先の日程・時間を第3希望まで入力できる）を表示する．
5. 生徒は，振替申請の内容を入力フォームに入力する．
6. 生徒は，「送信」ボタンを押下する．
7. システムは，表示中の入力フォームの各欄がすべて入力済みであることを確認する． 
8. システムは，申請内容を記録する．
9. システムは，元の画面に戻る．

## 代替フロー
### 代替フロー1
- 3a.1  基本フロー1で欠席したレッスンが存在しない場合，システムは申請エラーを出し，1に戻る．
### 代替フロー2
- 7a.1 基本フロー4でフォームに未入力の欄が存在する場合，システムは入力エラーを出し，5に戻る．
### 代替フロー3
- 8a.1 基本フロー8で申請内容の記録に失敗した場合，システムは申請エラーを出し，4に戻る．

# GUI紙芝居
## 欠席申請フォーム画面
<img src="img/usecase06.png">