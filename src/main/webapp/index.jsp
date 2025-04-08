<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form action="AuthServlet" method="post">
            <input type="text" name="email" id="" placeholder="ex: edouardo@gmail.com">
            <input type="password" name="password" id="" placeholder="ex: 12345678">
            <br>
            <input type="submit" value="VALIDER">
            <h3>BackOffice</h3>
                <ul>
                    <li>email: back@gmail.com</li>
                    <li>password: back</li>
                </ul>
            <h3>FrontOffice</h3>
                <ul>
                    <li>email: front@gmail.com</li>
                    <li>password: front</li>
                </ul>
        </form>
    </body>
</html>