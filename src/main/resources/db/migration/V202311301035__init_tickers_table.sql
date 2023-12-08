CREATE TABLE IF NOT EXISTS Tickers(
    tokenId UUID PRIMARY KEY,
    symbol VARCHAR(30) UNIQUE NOT NULL ,
    lastPrice VARCHAR(50) NOT NULL,
    highPrice24h VARCHAR(50),
    lowPrice24h VARCHAR(50),
    volume24h VARCHAR(50),
    turnOver24h VARCHAR(50),
    price24hPcnt VARCHAR(15),
    usdIndex VARCHAR(80)
)