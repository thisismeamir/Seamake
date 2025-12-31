# Seamake - CMake Project Analyzer

Seamake is a comprehensive CMake analyzer and helper tool designed for development of build automation scripts, particularly for HepSW and Unipac package managers. It provides deep analysis of CMake projects, extracting information about targets, dependencies, options, and build configurations.

## Features

- **Complete Project Analysis**: Analyzes entire CMake projects including subdirectories
- **Dependency Detection**: Identifies all dependencies with versions, components, and constraints
- **Target Information**: Extracts all build targets (executables, libraries) with their properties
- **Build Options**: Discovers all configurable options and their defaults
- **Extensible Architecture**: Support for custom edge case handlers
- **Multiple Report Formats**: Text, JSON, and Graphviz dependency graphs
- **Recursive Analysis**: Handles complex projects with multiple subdirectories

## Troubleshooting

### Parser Errors

If you encounter parsing errors:
1. Check that CMakeLists.txt is valid CMake syntax
2. Look for unsupported or non-standard commands
3. Create an edge case handler for custom patterns

### Missing Dependencies

If dependencies are not detected:
1. Check if they use standard CMake commands
2. Verify variable references are resolved
3. Add custom handler for proprietary formats

### Incomplete Target Information

If target information is incomplete:
1. Check if targets are defined conditionally
2. Verify include files are processed
3. Enable conditional block handler

## Contributing

To add support for new CMake patterns:

1. Implement `EdgeCaseHandler`
2. Add tests
3. Document the handler
4. Submit with examples

## Future Enhancements

Potential improvements:
- Full generator expression evaluation
- Macro/function expansion
- Cross-compilation support
- Cache variable tracking
- Target property inheritance
- Install rules analysis
- CTest configuration extraction
- CPack configuration extraction

## License

This project is licensed under Apache-2.0 License.
