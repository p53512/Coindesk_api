# Coindesk_api 說明如下
======================================================
1.IntegrationTesting 為整合測試裡面包含規格書所要求6個api測試方法
2.TestGetCoin 查詢所有幣別測試
3.TestGetCoin 查詢單一幣別測試
4.TestCreateCoin 新增幣別對應表資料測試
5.TestUpdateCoin 更新幣別對應表資料測試
6.TestDeleteCoin 刪除幣別對應表資料測試
7.TestCallCoindesApi 測試呼叫 coindesk API 
8.TestTurnCoinApi 測試呼叫資料轉換的 API 

建立 SQL 語法 檔案路徑 /resources/sql/schema.sql
======================================================
 CREATE TABLE Coindesk (
        CODE VARCHAR(30) PRIMARY KEY,
        CODENAME VARCHAR(10),
        SYMBOL  VARCHAR(30),
        RATE_FLOAT DOUBLE,
        RATE  VARCHAR(50),
        DESCRIPTION VARCHAR(50)
    );
