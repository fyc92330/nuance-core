## Internal Communication Structure

### Message
被傳遞的物件, 會是個 `key - value` 的載體

### MessageQueue
作為傳遞物件的核心, 允許 `Multiple` 存在, 允許透視

### MessageEvent
傳遞物件的事件, 交由對應的 `Listener` 去執行相對應的邏輯

### MessageListener
處理事件的監聽器, 按照邏輯將 `Event` 物件重組後丟到對應的邏輯層or監聽器, 或者是 `Exchange` 

### MessageExchange
內部結構的 Router, 決定邏輯線的模式：
1. Simple
2. Worker
3. Publisher
4. Routing
5. Topic

### MessageFetcher
處理事件的監聽器, 跟 `Listener` 的差別在於 `Fetcher` 是自主向 `Queue` 取得資訊