<#assign curNav="create">
<#include "head.ftl">
<@head>
<title>条件单</title>
<style>
</style>
</@head>
<div class="container">
    <form role="form" method="post" action="order-save-result.html">
        <div class="form-group">
            <label for="strategyId">策略名称</label>
            <select class="form-control" id="strategyId">
                <option>MACD金叉死叉策略</option>
                <option>格兰八法</option>
                <option>BOLL策略</option>
            </select>
        </div>
        <div class="form-group">
            <label class="control-label">证券名称</label>

            <p class="form-control-static" id="name">浦发银行</p>
        </div>
        <div class="form-group">
            <label for="code" class="control-label">证券代码</label>
            <input type="text" class="form-control" id="code" name="code" placeholder="请输入证券代码" value="600000">
        </div>
        <div class="form-group">
            <label for="entrustQuantity" class="control-label">委托数量</label>
            <input type="text" class="form-control" id="entrustQuantity" name="code" placeholder="请输入委托数量" value="600">
        </div>
        <div class="form-group">
            <label for="strategyParam" class="control-label">策略参数 A</label>
            <input type="text" class="form-control" id="strategyParam" name="code" placeholder="1~999" value="12">
        </div>
        <div class="form-group">
            <label for="strategyParam2" class="control-label">策略参数 B</label>
            <input type="text" class="form-control" id="strategyParam2" name="code" placeholder="1~9999" value="26">
        </div>
        <div class="form-group">
            <label for="strategyParam3" class="control-label">策略参数 C</label>
            <input type="text" class="form-control" id="strategyParam3" name="code" placeholder="1~9999" value="9">
        </div>
        <div class="form-group">
            <label for="dtp_input3" class="control-label">开始时间</label>

            <div class="input-group date form_time col-lg-6" data-date="" data-date-format="hh:ii"
                 data-link-field="dtp_input3" data-link-format="hh:ii">
                <input class="form-control" size="16" type="text" value="" readonly>
                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
            </div>
            <input type="hidden" id="dtp_input3" value=""/><br/>
        </div>
        <div class="form-group">
            <label for="dtp_input4" class="control-label">截止时间</label>

            <div class="input-group date form_time col-lg-6" data-date="" data-date-format="hh:ii"
                 data-link-field="dtp_input4" data-link-format="hh:ii">
                <input class="form-control" size="16" type="text" value="" readonly>
                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
            </div>
            <input type="hidden" id="dtp_input4" value=""/><br/>
        </div>
        <button type="submit" class="btn btn-primary">提交</button>
    </form>
</div>
<#include "foot.ftl">