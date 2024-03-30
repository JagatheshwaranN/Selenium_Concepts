class MyWebComponent extends HTMLElement {

    constructor(){
        super();
        this.myRoot = this.attachShadow({
            mode: "closed"
        })
    }
    connectedCallback() {
        this.myRoot.innerHTML = `<p>Shadow DOM Closed Automation</p>`;
    }

}
window.customElements.define("my-web-component", MyWebComponent);