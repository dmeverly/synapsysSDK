.PHONY: package run debug clean

JAVA := java
JAVAC := javac
JAR := jar

SRC_DIR := .
BUILD_DIR := out
CLASSES_DIR := $(BUILD_DIR)/classes
JARS_DIR := $(BUILD_DIR)/jars

TEST_MAIN := tst.java.tstapp
RUN_COMPONENT := $(TEST_MAIN)
RUN_ARGS :=

ifeq ($(OS),Windows_NT)
	CPSEP := ;
else
	CPSEP := :
endif

SOURCES := $(shell find $(SRC_DIR) -name '*.java' -not -path "./$(BUILD_DIR)/*" -not -path "./$(JARS_DIR)/*")

$(CLASSES_DIR)/.compiled: $(SOURCES)
	@echo "Compiling Java sources..."
	@mkdir -p $(CLASSES_DIR)
	$(JAVAC) -d $(CLASSES_DIR) $(SOURCES)
	@touch $@


.PHONY: package
package: $(CLASSES_DIR)/.compiled
	@echo "Packaging per-package jars into $(JARS_DIR)"
	@mkdir -p $(JARS_DIR)
	@for d in $(CLASSES_DIR)/*; do \
		if [ -d "$$d" ]; then \
			pkg=$$(basename "$$d"); \
			if [ "$$pkg" = "tst" ]; then continue; fi; \
			echo "  - creating $$pkg.jar"; \
			$(JAR) cf $(JARS_DIR)/$$pkg.jar -C $(CLASSES_DIR) $$pkg; \
		fi; \
	done

.PHONY: run
run: package
	@echo "Starting $(RUN_COMPONENT)..."
	$(JAVA) -cp "$(JARS_DIR)/*$(CPSEP)$(CLASSES_DIR)" $(RUN_COMPONENT) $(RUN_ARGS)

.PHONY: debug
debug: RUN_ARGS := debug
debug: run

.PHONY: debug
debug: RUN_ARGS := --debug
debug: run

.PHONY: clean
clean:
	@echo "Cleaning build output..."
	@rm -rf $(BUILD_DIR)