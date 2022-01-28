import React from "react";

function Checkbox({ active }) {
    return (
        <div>
            {active ? (
                <img src="img/checkbox_active.svg" />
            ) : (
                <img src="img/checkbox_inactive.svg" />
            )}
        </div>
    );
}

export default Checkbox;
