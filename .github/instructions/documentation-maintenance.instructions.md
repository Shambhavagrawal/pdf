---
applyTo: ".github/**/*.md,.github/**/*.instructions.md"
---

# Self-Maintaining Documentation Guidelines

This file provides instructions for how Copilot and developers should maintain the project's documentation and instruction files as the project evolves.

## Core Principle
**These instruction files should evolve with the project** - not remain static. As patterns change, new technologies are adopted, or better practices are discovered, these files must be updated.

## When to Update Documentation

### 1. Architecture Changes
**Update**: `.github/copilot-instructions.md`

When any of these change:
- Project structure or package organization
- Core dependencies (Spring Boot version, Java version, major libraries)
- Build tools or build process
- Testing frameworks or patterns
- Configuration approach (properties, YAML, environment variables)

**Example trigger**: "We upgraded from Spring Boot 3.5.7 to 3.6.0"
**Action**: Update version numbers, test timings, and any new features in main instructions

### 2. Development Pattern Changes
**Update**: Path-specific instruction files (e.g., `controller-guidelines.instructions.md`)

When any of these change:
- REST endpoint patterns or conventions
- DTO structure or validation approach
- Error handling strategy
- OpenAPI documentation style
- Security patterns
- Database access patterns (when added)

**Example trigger**: "We changed from manual error responses to using ProblemDetail"
**Action**: Update controller guidelines with new error handling pattern

### 3. Build & Deployment Changes
**Update**: `.github/copilot-instructions.md` - Build section

When any of these change:
- Build command sequence
- New build or test scripts
- CI/CD pipeline additions
- Docker or container configuration
- Deployment process

**Example trigger**: "We added GitHub Actions for automated testing"
**Action**: Document the workflow, update pre-commit checklist

### 4. New Features or Modules
**Update**: Create new path-specific instruction files if needed

When any of these are added:
- New major feature area (e.g., authentication, database layer)
- New package with specific conventions
- New technology integration

**Example trigger**: "We added Spring Security and authentication"
**Action**: Create `authentication-guidelines.instructions.md` with auth patterns

## Update Checklist

When updating any instruction file, ensure:
- [ ] Examples are based on actual project code, not hypothetical
- [ ] Commands have been tested and timing information is accurate
- [ ] File paths are correct and up-to-date
- [ ] Version numbers match current dependencies
- [ ] New patterns are demonstrated with real code snippets
- [ ] Obsolete information is removed, not just commented out
- [ ] Cross-references between files are updated (e.g., "see controller-guidelines.instructions.md")
- [ ] The "Self-Maintenance Instructions" section in each file is reviewed

## GitHub Copilot Best Practices Reference

Based on official GitHub Copilot guidelines, maintain these standards:

### Length
- **Main instructions**: Keep under 2 pages (~1000-1500 words)
- **Path-specific instructions**: 1-2 pages per file
- **Move detailed examples to path-specific files** when main file gets too long

### Content Priority
1. **High-level project overview** (what, why, tech stack)
2. **Validated build & test commands** (exact commands that work, with timing)
3. **Project structure** (where things are)
4. **Mandatory patterns** (critical conventions)
5. **Common errors and solutions** (what fails and how to fix it)
6. **Agent guidelines** (how to minimize exploration)

### Writing Style
- Use imperative mood ("Run `mvnw.cmd test`", not "You can run tests with...")
- Be specific ("Change `server.port` in `application.properties`", not "Change the port")
- Include exact commands, not descriptions of commands
- Document timing for long operations ("takes ~30s first run")
- Explicitly state "always" or "never" for critical rules

### What to Include
✅ **Include**:
- Exact commands that work
- Common error messages and solutions
- File paths and package names
- Build/test/run timing information
- Prerequisites and dependencies
- Critical patterns that must be followed

❌ **Exclude**:
- Basic programming concepts
- Framework fundamentals (assume knowledge of Spring Boot)
- IDE-specific instructions (focus on command-line)
- Overly detailed code walkthroughs
- Historical context or changelog information

## Updating Process

### For Copilot/AI Agents
When you (Copilot) make changes that affect patterns:

1. **Detect the change**: If you're creating/modifying files that establish new patterns
2. **Check existing instructions**: Read relevant instruction files
3. **Determine if update needed**: Is this a one-off or a new pattern?
4. **Update instruction files**: Add examples, update guidelines
5. **Verify consistency**: Ensure all instruction files are aligned

### For Human Developers
When you make architectural or pattern changes:

1. **Update relevant instruction files immediately** - don't defer
2. **Test the new commands/patterns** before documenting
3. **Remove obsolete information** rather than marking as deprecated
4. **Use real code snippets** from the actual project
5. **Commit instruction updates with code changes** in the same PR

## Validation Steps

After updating any instruction file:

1. **Test commands**: Ensure all documented commands actually work
2. **Verify paths**: Check that all file paths are correct
3. **Check cross-references**: Ensure links between files work
4. **Review length**: Confirm main file is still under 2 pages
5. **Validate examples**: Ensure code snippets compile and follow project patterns
6. **Test with Copilot**: Ask Copilot to create a new controller/DTO using updated instructions

## Template for New Path-Specific Instructions

When creating a new path-specific instruction file:

```markdown
---
applyTo: "path/pattern/**/*.java"
---

# [Feature/Area] Development Guidelines

## Overview
Brief description of what this area covers

## Mandatory Patterns
Critical rules that must be followed

## Examples
Real code snippets from the project

## Common Issues
Errors developers encounter and solutions

## Self-Maintenance Instructions
How to keep this file updated as patterns evolve
```

## Meta: Updating This File

This file itself should be updated when:
- GitHub Copilot publishes new guidelines or best practices
- Team discovers better documentation patterns
- New types of changes require different update strategies
- The update process itself needs improvement

### Alignment with GitHub Copilot Guidelines
On each scheduled review (at least once per release cycle) or when GitHub Copilot publishes significant guideline updates:
1. Retrieve latest Copilot documentation/best-practice summary.
2. Diff against this file and `.github/copilot-instructions.md` focusing on: update cadence, mandatory pattern wording, length/structure recommendations, and agent workflow steps.
3. Apply divergences where they improve clarity or enforcement; avoid adopting suggestions that conflict with project-specific constraints.
4. Update the "Last updated" and set a new "Next review" date.
5. Re-run `mvnw.cmd clean package` to regenerate Javadoc metadata if any endpoint/pattern examples changed.

Record below:
**Last updated**: 2025-11-15 (Initial creation; Copilot alignment added)
**Next review**: 2025-12-31 (or sooner upon major Copilot update)