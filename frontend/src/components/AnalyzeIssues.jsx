import React, { useState, useEffect } from "react";
import {
  TextField,
  Button,
  Card,
  CardContent,
  Typography,
  CircularProgress,
} from "@mui/material";
import { analyzeIssues } from "../services/api";

const AnalyzeIssues = ({ repoValue, setAnalysis }) => {
  const [repo, setRepo] = useState("");
  const [prompt, setPrompt] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (repoValue) setRepo(repoValue);
  }, [repoValue]);

  const handleAnalyze = async () => {
    if (!repo || !prompt) return;

    try {
      setLoading(true);
      const res = await analyzeIssues(repo, prompt);
      setAnalysis(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card sx={{ mb: 3 }}>
      <CardContent>
        <Typography variant="h6">Analyze Issues 2 </Typography>

        <TextField
          fullWidth
          label="Repository Name"
          margin="normal"
          value={repo}
          onChange={(e) => setRepo(e.target.value)}
        />

        {/* ✅ TEXTAREA */}
        <TextField
          fullWidth
          label="Analysis Prompt"
          margin="normal"
          multiline
          rows={4}
          value={prompt}
          onChange={(e) => setPrompt(e.target.value)}
          placeholder="E.g. Find recurring bugs, categorize issues, suggest fixes..."
        />

        <Button
          variant="contained"
          onClick={handleAnalyze}
          disabled={loading}
        >
          {loading ? <CircularProgress size={24} /> : "Analyze Issues"}
        </Button>
      </CardContent>
    </Card>
  );
};

export default AnalyzeIssues;