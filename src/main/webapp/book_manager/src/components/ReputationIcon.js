import React from "react";

function ReputationIcon({ inactive }) {
    return (
        <div>
            {inactive ? (
                <img src="img/reputation_inactive.svg" />
            ) : (
                <img src="img/reputation_active.svg" />
            )}
        </div>
    );
}

export default ReputationIcon;
