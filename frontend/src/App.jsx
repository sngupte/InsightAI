import React, { useState } from "react";
import { Container, Typography } from "@mui/material";
import ScanRepo from "./components/ScanRepo";
import AnalyzeIssues from "./components/AnalyzeIssues";
import Results from "./components/Results";

const App = () => {
  const [analysis, setAnalysis] = useState(null);
  const [repoValue, setRepoValue] = useState("");

  return (
    <Container maxWidth="md" sx={{ mt: 5 }}>
      <Typography variant="h4" gutterBottom>
        Issue Analyzer
      </Typography>

      <ScanRepo setRepoValue={setRepoValue} />

      <AnalyzeIssues
        repoValue={repoValue}
        setAnalysis={setAnalysis}
      />

      <Results analysis={analysis} />
    </Container>
  );
};

export default App;