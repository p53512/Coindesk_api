# Coindesk_api 說明如下
  1.IntegrationTesting 為整合測試裡面包含規格書所要求6個api測試方法<br> 
  2.TestGetCoin 查詢所有幣別測試<br> 
  3.TestGetCoin 查詢單一幣別測試<br> 
  4.TestCreateCoin 新增幣別對應表資料測試<br> 
  5.TestUpdateCoin 更新幣別對應表資料測試<br> 
  6.TestDeleteCoin 刪除幣別對應表資料測試<br> 
  7.TestCallCoindesApi 測試呼叫 coindesk API <br> 
  8.TestTurnCoinApi 測試呼叫資料轉換的 API <br> 

建立 SQL 語法 檔案路徑 /resources/sql/schema.sql
======================================================
   >CREATE TABLE Coindesk (<br> 
          >>CODE VARCHAR(30) PRIMARY KEY,<br> 
          >>CODENAME VARCHAR(10),<br> 
          >>SYMBOL  VARCHAR(30),<br> 
         >> RATE_FLOAT DOUBLE,<br> 
          >>RATE  VARCHAR(50),<br> 
         >> DESCRIPTION VARCHAR(50)<br> 
      >>);<br> 
