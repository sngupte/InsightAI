import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/githubinsightai",
  headers: {
    "Content-Type": "application/json",
  },
});

export const scanRepo = (repo) =>
  API.post("/scan", { repo });

export const analyzeIssues = (repo, prompt) =>
  API.post("/analyze", {
    repo,
    prompt,
  });