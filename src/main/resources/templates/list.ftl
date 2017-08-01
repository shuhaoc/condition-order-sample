<#assign curNav="list">
<#include "head.ftl">
<@head>
<title>条件单</title>
<style>
    .security-id {
        margin-right: 3%;
    }
</style>
</@head>
<div class="panel-group" id="accordion">
    <div class="panel panel-default">
        <div class="panel-heading" data-toggle="collapse" data-parent="#accordion"
             href="#collapse1">
            <h4 class="panel-title">
                <p class="panel-title">
                    <span class="security-id">浦发银行&nbsp;600000.SH</span>
                    <span class="label label-primary">已暂停</span></p>
            </h4>
        </div>
        <div class="panel-body" data-toggle="collapse" data-parent="#accordion"
             href="#collapse1">
            <p style="font-weight: bold">格兰八法策略</p>

            <p style="color: grey">DIF: 2.34 DEA: 1.23 MACD: 1.11</p>

            <p>交易方向：买入 委托数量：100股</p>

            <p>开始时间：14:50 截止时间：15:00</p>
        </div>
        <div id="collapse1" class="panel-collapse collapse">
            <div class="panel-footer">
                <a href="order-save.html">
                    <button type="button" class="btn btn-default">修改</button>
                </a>
                <button type="button" class="btn btn-default">暂停</button>
                <button type="button" class="btn btn-default">删除</button>
                <button type="button" class="btn btn-default">详情</button>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" data-toggle="collapse" data-parent="#accordion"
             href="#collapse2">
            <h4 class="panel-title">
                <p class="panel-title">中信证券&nbsp;600001.SH</p>
            </h4>
        </div>
        <div class="panel-body" data-toggle="collapse" data-parent="#accordion"
             href="#collapse2">
            <p style="font-weight: bold">MACD金叉死叉策略</p>

            <p style="color: green">DIF: 2.34 DEA: 1.23 MACD: 1.11</p>

            <p>交易方向：买入 委托数量：100股</p>

            <p>开始时间：14:50 截止时间：15:00</p>
        </div>
        <div id="collapse2" class="panel-collapse collapse">
            <div class="panel-footer">
                <a href="order-save.html">
                    <button type="button" class="btn btn-default">修改</button>
                </a>
                <button type="button" class="btn btn-default">暂停</button>
                <button type="button" class="btn btn-default">删除</button>
                <button type="button" class="btn btn-default">详情</button>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" data-toggle="collapse" data-parent="#accordion"
             href="#collapse3">
            <h4 class="panel-title">
                <p class="panel-title">浦发银行&nbsp;600000.SH</p>
            </h4>
        </div>
        <div class="panel-body" data-toggle="collapse" data-parent="#accordion"
             href="#collapse3">
            <p style="font-weight: bold">MACD金叉死叉策略</p>

            <p style="color: green">DIF: 2.34 DEA: 1.23 MACD: 1.11</p>

            <p>交易方向：买入 委托数量：100股</p>

            <p>开始时间：14:50 截止时间：15:00</p>
        </div>
        <div id="collapse3" class="panel-collapse collapse">
            <div class="panel-footer">
                <a href="order-save.html">
                    <button type="button" class="btn btn-default">修改</button>
                </a>
                <button type="button" class="btn btn-default">暂停</button>
                <button type="button" class="btn btn-default">删除</button>
                <button type="button" class="btn btn-default">详情</button>
            </div>
        </div>
    </div>
</div>

<#include "foot.ftl">