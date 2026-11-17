# Deploying CareerBridge (GitHub + Render, free, no credit card)

1. Put this folder in a GitHub repository (Add file -> Upload files; commit to `main`). Do not upload passwords or API keys: none are needed.
2. Sign up at render.com with your GitHub account.
3. New -> Web Service -> pick the repository.
   - Language / Runtime: **Docker** (uses the `Dockerfile` in this folder)
   - Instance type: **Free**
   - Health Check Path: `/api/health`
4. Create Web Service. The first build takes a few minutes. The public HTTPS address looks like `https://careerbridge-xxxx.onrender.com`.
5. Open the address, sign up, then open `/api/health` (it must show `"status":"UP"` and `"database":"UP"`).

Settings (environment variables, all optional): `DATABASE_URL` (managed PostgreSQL, see below), `DB_URL`, `DB_USER`, `DB_PASSWORD`, `GOOGLE_CLIENT_ID`. Render supplies `PORT` automatically.

Notes
- Free services go to sleep after about 15 minutes without visitors; the first request afterwards takes up to a minute.
- With the default embedded H2 database the data is stored inside the container and is lost when Render restarts or redeploys it. Use the managed PostgreSQL database below for permanent data.
- Updates: push to GitHub and Render redeploys automatically.

## Permanent data: managed PostgreSQL on Render
1. Render dashboard -> New -> PostgreSQL. Name `careerbridge-db`, same Region as the web service, Instance type Free, Create Database.
2. Open the database page -> Connections -> copy the **Internal Database URL**.
3. Open the web service -> Environment -> Add Environment Variable: Key `DATABASE_URL`, Value = the copied URL -> Save Changes (Render redeploys).
4. Open `/api/health`: `"database":"UP"`. Sign up, then Manual Deploy: the account is still there. Tables `users` and `applications` are created automatically.

Never commit the URL (it contains the password); it only lives in Render's Environment settings.
