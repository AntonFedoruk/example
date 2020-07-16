<#macro page>
    <!DOCTYPE HTML>
    <html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <#-- we tell the browser that the pixel density on the screen must be taken into account (the scale will adjust to the screen)-->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
        <link rel="stylesheet" href="/static/style.css"/>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous"/>
        <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>
    <body>
    <#include "navbar.ftlh">
    <div class="container mt-5"> <#--Spacing utilities
            The classes are named using the format {property}{sides}-{size} for xs and {property}{sides}-{breakpoint}-{size}
        for sm, md, lg, and xl.
            Where property is one of:
        m - for classes that set margin
        p - for classes that set padding
            Where sides is one of:
        t - for classes that set margin-top or padding-top
        b - for classes that set margin-bottom or padding-bottom
        l - for classes that set margin-left or padding-left
        r - for classes that set margin-right or padding-right
        x - for classes that set both *-left and *-right
        y - for classes that set both *-top and *-bottom
        blank - for classes that set a margin or padding on all 4 sides of the element
            Where size is one of:
        0 - for classes that eliminate the margin or padding by setting it to 0
        1 - (by default) for classes that set the margin or padding to $spacer * .25
        2 - (by default) for classes that set the margin or padding to $spacer * .5
        3 - (by default) for classes that set the margin or padding to $spacer
        4 - (by default) for classes that set the margin or padding to $spacer * 1.5
        5 - (by default) for classes that set the margin or padding to $spacer * 3
        auto - for classes that set the margin to auto-->
        <#nested>
    </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>
