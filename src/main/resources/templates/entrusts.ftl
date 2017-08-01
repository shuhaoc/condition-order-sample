<#assign curNav="entrusts">
<#include "head.ftl">
<@head>
<title>条件单</title>
</@head>
<div class="panel-group" id="accordion">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h4 class="panel-title">
                <p class="panel-title">
                    <span class="security-id">
                        <span class="security-name">浦发银行</span><span class="security-code">600000.SH</span>
                    </span>
                    <span class="label label-primary">价格</span>
                </p>
            </h4>
        </div>
        <div class="panel-body">
            <p>委托价格：17.30元 委托数量：100股</p>

            <p>委托时间：2017-08-01 14:50:00</p>

            <p style="color: #3231de">已申报未成交</p>
        </div>
    </div>
    <div class="panel panel-success">
        <div class="panel-heading">
            <h4 class="panel-title">
                <p class="panel-title">
                    <span class="security-id">
                        <span class="security-name">浦发银行</span><span class="security-code">600000.SH</span>
                    </span>
                    <span class="label label-primary">价格</span>
                </p>
            </h4>
        </div>
        <div class="panel-body">
            <p>委托价格：17.30元 委托数量：100股</p>

            <p>委托时间：2017-08-01 14:50:00</p>

            <p style="color: #3231de">已申报未成交</p>
        </div>
    </div>
    <div class="panel panel-danger">
        <div class="panel-heading" data-toggle="collapse" data-parent="#accordion"
             href="#collapse2">
            <h4 class="panel-title">
                <p class="panel-title">
                    <span class="security-id">
                        <span class="security-name">浦发银行</span><span class="security-code">600000.SH</span>
                    </span>
                    <span class="label label-primary">拐点买入</span>
                </p>
            </h4>
        </div>
        <div class="panel-body" data-toggle="collapse" data-parent="#accordion"
             href="#collapse2">

            <p>委托价格：17.30元 委托数量：100股</p>

            <p>委托时间：2017-08-01 14:50:00</p>

            <p style="color: #ff063c">金额不足</p>
        </div>
    </div>
</div>
<ul class="pager">
    <li><a href="#">&lt;&lt; 往后</a></li>
    <li><a href="#">往前 &gt;&gt;</a></li>
</ul>
<#include "foot.ftl">