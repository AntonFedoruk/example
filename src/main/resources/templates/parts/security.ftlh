<#--With this you can create a new variable, or replace an existing variable. Note that only top-level variables can be created/replaced (i.e. you can't create/replace some_hash.subvar, but some_hash).-->
<#assign
know = Session.SPRING_SECURITY_CONTEXT??
> <#-- if this obj is defined in context -> we can work with users session -->
<#if know>
<#--define set of necessary data-->
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal <#--'user' will contain our user, that we described in DB and let us to use any of his methods  -->
        name = user.getUsername()
        isAdmin = user.isAdmin()
        currentUserId = user.getId()
    >
<#else>
    <#assign
        name = "unkmown"
        isAdmin = false
        currentUserId = -1
    >
</#if>