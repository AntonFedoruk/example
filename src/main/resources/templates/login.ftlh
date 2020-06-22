<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    ${message?ifExists}
    <@l.login "/login" false/> <#-- false: is an isRegisterForm variable, it`s  need to be false to complete checking(<#if !isRegisterForm><a href="/registration">Add new user</a></#if>) to don`t display corresponding link -->
</@c.page>