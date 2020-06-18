<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="mb-1">Add new user</div> <#-- class="mb-1": added little margin below -->
    ${message?ifExists}
    <@l.login "/registration" true/><#-- true: is an isRegisterForm variable, it`s  need to be false to complete checking(<#if !isRegisterForm><a href="/registration">Add new user</a></#if>) to display corresponding link -->
</@c.page>