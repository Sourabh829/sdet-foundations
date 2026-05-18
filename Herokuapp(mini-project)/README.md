# Herokuapp Mini Project

Mini end-to-end Selenium + TestNG automation project for `the-internet.herokuapp.com`.

## Scenarios Covered

1. Valid login with the site-provided credentials.
2. Logout after successful login.
3. Invalid username error.
4. Invalid password error.
5. Signup availability check.

Note: `the-internet.herokuapp.com` does not provide a real signup or registration page. The signup scenario verifies that the application has no exposed signup flow, then keeps the project focused on the real authentication flow available on the site.

## Run

```powershell
mvn test
```

Run in headed browser mode:

```powershell
mvn test -Dheadless=false
```
