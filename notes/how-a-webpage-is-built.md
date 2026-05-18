# How a Webpage Is Built — For Testers

> **Start here:** Selenium needs an _address_ to find a button.  
> That address is **XPath**. XPath navigates the **DOM**. The DOM is built from **HTML**. CSS controls what you _see_, but `id` and `class` are your locator hooks.

---

## 1. HTML — The Skeleton

HTML is a tree of **tags**. Every tag has a purpose. Attributes describe it.

```html
<!-- From: the-internet.herokuapp.com/login -->
<form id="login" action="/authenticate" method="post">
  <label for="username">Username</label>
  <input type="text" name="username" id="username">

  <label for="password">Password</label>
  <input type="password" name="password" id="password">

  <button class="radius" type="submit">Login</button>
</form>
```

| Part | Example | What it is |
|------|---------|------------|
| Tag | `<input>` | The element type |
| Attribute | `type="text"` | Property of the element |
| `id` | `id="username"` | Unique on the page — best locator |
| `class` | `class="radius"` | Shared style group — good locator |
| `data-*` | `data-test="login-button"` | Custom attribute, often added for testing |

> **Tester rule:** `id` is unique. `class` can repeat. `data-test` is your gift from devs.

---

## 2. CSS — The Skin (Care About `id` and `class`)

CSS makes things look good. As a tester you don't style — but CSS _selectors_ use the same `id` and `class` attributes you'll use in XPath.

```css
/* CSS uses  #id  and  .class  — same attributes, different syntax */
#login-button  { background: green; }   /* targets id="login-button" */
.btn_action    { padding: 10px; }       /* targets class="btn_action" */
```

From SauceDemo (`saucedemo.com`):
```html
<input id="user-name" class="form_input" data-test="username" type="text">
<input id="password"  class="form_input" data-test="password" type="password">
<input id="login-button" class="btn_action" data-test="login-button" type="submit">
```

CSS says `form_input` means "style this as a form field." You say "`form_input` means I can find both inputs with the same class."

---

## 3. The DOM — The Live Tree

The DOM (Document Object Model) is what the **browser builds** from HTML. It's not the `.html` file — it's a live, queryable tree of objects in memory.

```
document
└── html
    └── body
        └── div#root                        ← id="root"
            └── div.login_container         ← class="login_container"
                └── form
                    ├── input#user-name     ← id="user-name"
                    ├── input#password      ← id="password"
                    └── input#login-button  ← id="login-button"
```

**Open DevTools → Elements tab** to see this tree live. You can hover any node and it highlights on the page.

From DemoQA (`demoqa.com/text-box`), notice nesting:
```html
<div id="userName-wrapper" class="mt-2 row">         <!-- container -->
  <div class="col-md-3">
    <label id="userName-label">Full Name</label>      <!-- label -->
  </div>
  <div class="col-md-9">
    <input id="userName" class="form-control" type="text">  <!-- target -->
  </div>
</div>
```

The input is 3 levels deep inside wrappers. XPath can reach it directly — or via its label.

---

## 4. XPath — The Address

XPath is a path through the DOM tree. Selenium, Playwright, and Appium all use it.

### Syntax Cheat Sheet

| Pattern | Meaning |
|---------|---------|
| `//tag` | Find `tag` anywhere in the page |
| `[@attr='val']` | Filter: attribute equals value |
| `[@attr]` | Filter: attribute exists |
| `contains(@attr, 'part')` | Partial attribute match |
| `text()='Login'` | Element's visible text equals |
| `contains(text(), 'Log')` | Partial text match |
| `//parent//child` | Find child inside parent |
| `following-sibling::` | Next sibling in the tree |

### Real Locators — From the 3 Sites

```xpath
<!-- saucedemo.com — by id (most stable) -->
//input[@id='user-name']
//input[@id='login-button']

<!-- saucedemo.com — by data-test attribute (built for automation) -->
//input[@data-test='username']
//input[@data-test='login-button']

<!-- the-internet.herokuapp.com/login — form + child input -->
//form[@id='login']//input[@type='password']

<!-- the-internet.herokuapp.com/tables — text inside a table cell -->
//table[@id='table1']//td[contains(text(),'Smith')]

<!-- demoqa.com/text-box — navigate from label to its sibling input -->
//label[text()='Full Name']/following-sibling::div/input

<!-- any page — button by visible text -->
//button[text()='Login']
//button[contains(text(),'Log')]
```

> **Prefer:** `@id` > `@data-test` > `@class` + text > position.  
> **Avoid:** `//div[3]/span[2]/input` — breaks on any layout change.

---

## 5. Quick Reference

| Concept | What it is | Why you care |
|---------|------------|--------------|
| **HTML tag** | `<input>`, `<button>`, `<div>` | Type of element you're targeting |
| **`id`** | Unique identifier on the page | Most reliable locator — use first |
| **`class`** | Style group, may repeat | Useful when no `id`; can be combined |
| **`data-test`** | Custom test attribute | Stable — devs add it for QA |
| **`type`** | `text`, `password`, `submit` | Differentiates identical-looking inputs |
| **DOM** | Browser's live element tree | What DevTools shows; what Selenium reads |
| **XPath** | Path expression to locate a node | Your locator strategy in any framework |
| **DevTools** | F12 → Elements tab | Inspect DOM, copy XPath, test selectors |

---

## DevTools Workflow

1. Open site → press **F12**
2. Click **Elements** tab — see the DOM tree
3. Press **Ctrl+F** inside Elements → type `#user-name` or `//input[@id='user-name']`
4. Right-click any element → **Copy → Copy XPath** (start here, then simplify)
5. Test XPath in Console: `$x('//input[@id="user-name"]')` → returns the element

---

*Sites used: [SauceDemo](https://www.saucedemo.com) · [DemoQA](https://demoqa.com) · [The Internet](https://the-internet.herokuapp.com)*
