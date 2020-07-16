<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" class="form-control mr-2" name="filter" value="${filter?ifExists}"
                       placeholder="Search by tag"/>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
    </div>
    <#include "parts/messageEdit.ftlh"/>
    <#include "parts/messageList.ftlh"/>
</@c.page>