import React from "react";
import ReactDOM from "react-dom";
import EnterEmail from "./EnterEmail";

it("renders without crashing", () => {
  const div = document.createElement("div");
  ReactDOM.render(<EnterEmail />, div);
  ReactDOM.unmountComponentAtNode(div);
});