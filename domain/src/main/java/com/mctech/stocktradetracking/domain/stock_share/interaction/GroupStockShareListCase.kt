package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

class GroupStockShareListCase {

  fun transform(stockShareList: List<StockShare>) = stockShareList
    .groupBy { it.code }
    .values
    .map {
      it.reduce { acc, stockShare ->
        StockShare(
          code = stockShare.code,
          initialShareAmount = acc.initialShareAmount + stockShare.initialShareAmount,
          shareAmount = acc.shareAmount + stockShare.shareAmount,
          initialPurchasePrice = (acc.getInvestmentValue() + stockShare.getInvestmentValue()) / (acc.initialShareAmount + stockShare.initialShareAmount),
          purchasePrice = (acc.getInvestmentValue() + stockShare.getInvestmentValue()) / (acc.shareAmount + stockShare.shareAmount),
          purchaseDate = stockShare.purchaseDate,
          salePrice = (acc.getFinalStockPrice() + stockShare.getFinalStockPrice()) / (acc.shareAmount + stockShare.shareAmount),
          marketChange = stockShare.marketChange,
          previousClose = stockShare.previousClose,
          isPositionOpened = stockShare.isPositionOpened
        )
      }
    }
}