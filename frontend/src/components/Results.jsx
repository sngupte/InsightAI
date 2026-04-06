import React from "react";
import {
  Card,
  CardContent,
  Typography,
} from "@mui/material";

const Results = ({ analysis }) => {
  if (!analysis) return null;

  return (
    <Card>
      <CardContent>
        <Typography variant="h6" gutterBottom>
          Analysis Results
        </Typography>

        <Typography
          variant="body1"
          sx={{
            whiteSpace: "pre-line", // 👈 preserves line breaks
            backgroundColor: "#f5f5f5",
            padding: 2,
            borderRadius: 2,
          }}
        >
          {analysis.analysis}
        </Typography>
      </CardContent>
    </Card>
  );
};

export default Results;