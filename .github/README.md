# GitHub Copilot Instructions - Documentation Guide

## Overview
This directory contains GitHub Copilot custom instructions optimized according to official GitHub Copilot best practices (November 2025).

## File Structure

```
.github/
├── copilot-instructions.md              # Main repository-wide instructions (~2 pages)
├── copilot-instructions-old.md          # Backup of previous version (for reference)
└── instructions/                         # Path-specific instruction files
    ├── controller-guidelines.instructions.md
    ├── dto-guidelines.instructions.md
    └── documentation-maintenance.instructions.md
```

## What Changed (November 15, 2025)

### ✨ Improvements
1. **Condensed main file** from 5+ pages to ~2 pages (GitHub recommendation)
2. **Created path-specific instructions** that apply automatically to relevant files
3. **Added validated build commands** with actual timing information
4. **Added self-maintenance guidelines** so docs evolve with the project
5. **Removed verbose examples** and moved to path-specific files
6. **Added explicit agent guidelines** to minimize exploration

### 📁 New Path-Specific Instructions
- **controller-guidelines.instructions.md**: Applies to `**/controller/**/*.java`
  - Detailed OpenAPI annotation examples
  - REST endpoint patterns
  - Response code standards
  - Validation patterns

- **dto-guidelines.instructions.md**: Applies to `**/dto/**/*.java`
  - Schema annotation examples
  - Validation annotation patterns
  - Common DTO patterns
  - Request/Response DTO examples

- **documentation-maintenance.instructions.md**: Applies to `.github/**/*.md`
  - How to keep instructions updated
  - When to update each file
  - GitHub Copilot best practices reference
  - Update process and validation steps

## How It Works

### For GitHub Copilot
When Copilot works on a file:
1. It **always** reads `.github/copilot-instructions.md` (repository-wide instructions)
2. If working on a controller file, it **also** reads `controller-guidelines.instructions.md`
3. If working on a DTO file, it **also** reads `dto-guidelines.instructions.md`
4. Instructions from all matching files are combined

### For Developers
- **Quick reference**: Read `copilot-instructions.md` for project overview
- **Detailed patterns**: Check path-specific files in `instructions/` for examples
- **Maintenance**: Follow `documentation-maintenance.instructions.md` when updating docs

## Self-Updating Feature

These instruction files are designed to **update themselves** as the project evolves:

### Automatic Updates
When Copilot makes changes that establish new patterns, it will:
1. Detect the new pattern
2. Check if it's documented in instruction files
3. Update relevant instruction files with examples
4. Ensure consistency across all documentation

### Manual Updates
When you make architectural changes:
1. Update `copilot-instructions.md` for project-wide changes
2. Update path-specific files for pattern changes
3. Commit instruction updates with code changes

### Update Triggers
Update instructions when:
- ✅ Dependencies or versions change
- ✅ New patterns are established (e.g., new validation approach)
- ✅ Build/test/run commands change
- ✅ Project structure is reorganized
- ✅ Common errors and solutions are discovered
- ✅ New major features are added

## Validation

To verify instructions are working:

```powershell
# 1. Test build commands
mvnw.cmd clean package

# 2. Run tests
mvnw.cmd test

# 3. Start application
mvnw.cmd spring-boot:run

# 4. Ask Copilot to create a new controller
# It should automatically follow the patterns in controller-guidelines.instructions.md

# 5. Ask Copilot to create a new DTO
# It should automatically follow the patterns in dto-guidelines.instructions.md
```

## GitHub Copilot Best Practices Applied

✅ **Main file under 2 pages**: Condensed from 5+ to ~2 pages  
✅ **Validated commands**: All commands tested with timing information  
✅ **Common errors documented**: Build issues and solutions included  
✅ **Path-specific instructions**: Detailed examples in context-specific files  
✅ **Agent guidelines**: Explicit instructions to minimize exploration  
✅ **Self-maintenance**: Instructions for keeping docs updated  
✅ **Real examples**: All code snippets from actual project files  
✅ **Specific language**: "Always run X", "Never use Y" for critical rules  

## References

- **GitHub Copilot Documentation**: https://docs.github.com/en/copilot/customizing-copilot/adding-custom-instructions-for-github-copilot
- **Official Best Practices**: See `documentation-maintenance.instructions.md` for detailed guidelines
- **Original Instructions**: Backed up in `copilot-instructions-old.md` for reference

## Maintenance Schedule

- **Review frequency**: After major project changes or dependency updates
- **Last updated**: 2025-11-15 (Initial optimization)
- **Next review**: When Spring Boot 4.x is adopted or major architectural changes occur

## Quick Tips

💡 **For Copilot Users**: Just ask Copilot to create files - it will automatically apply the relevant guidelines

💡 **For Manual Coding**: Refer to path-specific instruction files for detailed patterns and examples

💡 **For Updates**: Follow the checklist in `documentation-maintenance.instructions.md`

💡 **For Validation**: Run the build/test commands to ensure instructions match reality

---

**Note**: The old comprehensive file is preserved as `copilot-instructions-old.md` for reference. It can be deleted once you've confirmed the new structure works well for your workflow.
