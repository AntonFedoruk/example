<#import "parts/common.ftlh" as c>

<@c.page>
    <h5>${username}</h5>
    ${message?ifExists}
    <form method="post"> <#--we haven`t 'action=""' because form will be sent to the same address from which it comes from-->
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-6">
                <input type="password" class="form-control" name="password" placeholder="Password"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-6">
                <input type="email" class="form-control" name="email" placeholder="some@some.com" value="${email!''}"/> <#--${email!''}: !'' if email is null then we will see empty string and app won't fall-->
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Save</button>
    </form>
</@c.page>