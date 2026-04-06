import React, { useState } from "react";
import {
  TextField,
  Button,
  Card,
  CardContent,
  Typography,
  CircularProgress,
} from "@mui/material";
import { scanRepo } from "../services/api";

const ScanRepo = ({ setRepoValue }) => {
  const [repo, setRepo] = useState("");
  const [result, setResult] = useState(null);
  const [errorResponse, setErrorResponse] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleScan = async () => {
    if (!repo) return;

    try {
      setLoading(true);

      const res = await scanRepo(repo);

      setResult(res.data);
      setRepoValue(repo);
    } catch (err) {
      setErrorResponse(err.response?.data?.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card sx={{ mb: 3 }}>
      <CardContent>
        <Typography variant="h6">Scan GitHub Repo</Typography>

        <TextField
          fullWidth
          label="Repository Name (owner/repo)"
          margin="normal"
          value={repo}
          onKeyDown={() => setResult(null)}
          onChange={(e) => setRepo(e.target.value)}
        />

        <Button variant="contained" onClick={handleScan} disabled={loading}>
          {loading ? <CircularProgress size={24} /> : "Scan Repo"}
        </Button>

        {/* ✅ Show API Response */}
        {result && (
          <Card sx={{ mt: 2, p: 2, backgroundColor: "#f5f5f5" }}>
            <Typography>
              <b>Repository:</b> {result.repo}
            </Typography>
            <Typography>
              <b>Issues Fetched:</b> {result.issuesFetched}
            </Typography>
            <Typography>
              <b>Status:</b> {result.storedStatus}
            </Typography>
          </Card>
        )}
        {errorResponse && (
          <Card sx={{ mt: 2, p: 2, backgroundColor: "#f5f5f5" }}>
            <Typography
              variant="body1"
              sx={{
                whiteSpace: "pre-line", // 👈 preserves line breaks
                backgroundColor: "#f5f5f5",
                padding: 2,
                borderRadius: 2,
              }}
            >
              {errorResponse}
            </Typography>
          </Card>
        )}
      </CardContent>
    </Card>
  );
};

export default ScanRepo;
