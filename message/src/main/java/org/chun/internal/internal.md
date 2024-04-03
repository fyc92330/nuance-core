## Internal Communication Structure

### Message
被傳遞的物件, 會是個 `key - value` 的載體

### MessageQueue
作為傳遞物件的核心, 允許 `Multiple` 存在, 允許透視

### MessageEvent
傳遞物件的事件, 交由對應的 `Listener` 去執行相對應的邏輯
[eventpublisher.publishevent]()

### MessageListener
處理事件的監聽器, 按照邏輯將 `Event` 物件重組後丟到對應的邏輯層or監聽器, 或者是 `Exchange` 
- T: 接收的 `MessageEvent`, 拆解成 `Message` 傳入 `Queue`
- R: 傳送對應的 `Queue`, 類型為 `Message` 對應的 `Queue`, 由 `ID` 辨識為哪一組
- 發送會call對應的 `MessageFetcher` 取得 `Queue` 內的 `Message`

### MessageExchange
內部結構的 Router, 決定邏輯線的模式：
1. Simple
2. Direct: MessageExchange 按照 QueueId 推送至對應的 Queue
3. Topic: MessageExchange 按照規則推送至對應的 Queue
4. Fanout: MessageExchange 直接推送全部的 Queue


### MessageFetcher
處理事件的監聽器, 跟 `Listener` 的差別在於 `Fetcher` 是自主向 `Queue` 取得資訊
1. T: 取得的 `Message`
2. R: `Message` 對應的 `Queue`, 從中取得消息(順序or指定)

### MessageQueueViewer
檢視 `Queue` 的內容

## 結構
1. `Message` 包成 `MessageEvent`, 經由 [publishEvent()] 給 Listener 拆解推送至對應的 Queue 並通知 Fetcher 取資料
2. `Message` 打入 `MessageExchange`, 會直接推送至 Queue 並通知 Fetcher
3. `MessageExchange` 和 `MessageListener` 應該要是 singleton
4. `MessageQueue` 和 `MessageFetcher` 應該要能彈性增加與關閉, `MessageFetcher` 的數量不應該超過 `MessageQueue`
5. 控管的 `GlobalMessageManager` 應該要是 Simple
6. `MessageExchangeType` 無論為何都會有一組預設啟用的 `Bean`, 按照流量決定彈性加減 `Queue` 和 `Fetcher`


