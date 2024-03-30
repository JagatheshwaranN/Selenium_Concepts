class MyWebComponent extends HTMLElement {

    constructor(){
        super();
        this.attachShadow({
            mode: "open"
        })
    }
    connectedCallback() {
        this.shadowRoot.innerHTML = `<p>Shadow DOM Open Automation</p>`;
    }

}
window.customElements.define("my-web-component", MyWebComponent);