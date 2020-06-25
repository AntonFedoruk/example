<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <@l.login "/login" false/> <#-- false: is an isRegisterForm variable, it`s  need to be false to complete checking(<#if !isRegisterForm><a href="/registration">Add new user</a></#if>) to don`t display corresponding link -->
</@c.page>