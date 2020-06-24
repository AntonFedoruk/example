<#include "security.ftlh">
<#import "login.ftlh" as l>

<#-- nav  - navigation panel-->
<nav class="navbar navbar-expand-lg navbar-light bg-light"> <#-- navbar-expand-lg - nav depends on screen width, responsive collapsing; navbar-light bg-light - describes color scheme-->
    <a class="navbar-brand" href="/">Sweater</a>
    <#-- menu button (will apear when screen is smaller than "-lg"(desktops, 992px and up)-->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse"
         id="navbarSupportedContent"> <#-- collapse - means that menu is collapsible(for grouping and hiding navbar contents)-->
        <ul class="navbar-nav mr-auto"> <#-- use ul(unordered list) as a base of  menu; navbar-nav mr-auto: means that menu will be auto generated-->
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/main">Messages</a>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">User list</a>
                </li>
            </#if>
            <#if user??> <#--'user' takes from security template, where user can be get only in case of his authentication -->
                <li class="nav-item">
                    <a class="nav-link" href="/user/profile">Profile</a>
                </li>
            </#if>
        </ul>
        <#--navbar-text:  for adding vertically centered strings of text-->
        <div class="navbar-text mr-3">${name}</div>
        <@l.logout/>
    </div>
</nav>